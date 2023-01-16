package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.dto.NotaSaidaDTO;
import br.gov.al.empresa.produto.dto.form.ItemSaidaFormDTO;
import br.gov.al.empresa.produto.dto.form.NotaSaidaFormDTO;
import br.gov.al.empresa.produto.entity.*;
import br.gov.al.empresa.produto.exception.BadRequestException;
import br.gov.al.empresa.produto.exception.RecordNotFoundException;
import br.gov.al.empresa.produto.repository.NotaSaidaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.CODE_DELETE_ERROR;
import static br.gov.al.empresa.produto.util.Constants.GenericConstants.CODE_SAVE_ERROR;

@Service
public class NotaSaidaService extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotaSaidaService.class);
    private static final String NAME = "notaSaida.name";

    private final NotaSaidaRepository repository;
    private final ItemService itemService;
    private final ItemEstoqueService itemEstoqueService;
    private final EmpresaService empresaService;
    private final ModelMapper mapper;

    @Autowired
    public NotaSaidaService(NotaSaidaRepository repository, ItemService itemService, ItemEstoqueService itemEstoqueService,
                            @Lazy EmpresaService empresaService, ModelMapper mapper, ResourceBundleMessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.itemService = itemService;
        this.itemEstoqueService = itemEstoqueService;
        this.empresaService = empresaService;
        this.mapper = mapper;
    }

    public List<NotaSaidaDTO> findAll() {
        Collection<NotaSaida> items = repository.findAll(Sort.by("data"));
        return items.stream().map(notaEntrada -> mapper.map(notaEntrada, NotaSaidaDTO.class)).collect(Collectors.toList());
    }

    public NotaSaidaDTO findById(Long id) {
        NotaSaida notaSaida = getById(id);
        return mapper.map(notaSaida, NotaSaidaDTO.class);
    }

    public List<NotaSaidaDTO> findAllSaidasByEmpresaId(UUID empresaId) {
        Collection<NotaSaida> items = repository.findByEmpresaId(empresaId);
        return items.stream().map(notaSaida -> mapper.map(notaSaida, NotaSaidaDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public NotaSaidaDTO save(NotaSaidaFormDTO notaSaidaFormDTO) {
        try {
            Empresa empresa = empresaService.findEmpresaById(notaSaidaFormDTO.getEmpresaId());

            NotaSaida notaSaida = new NotaSaida();
            notaSaida.setEmpresa(empresa);
            notaSaida.setData(notaSaidaFormDTO.getData());

            Double percentualDesconto = notaSaidaFormDTO.getDescontoPercentual();

            Set<ItemSaida> itensSaida = getItensSaida(notaSaida, empresa, notaSaidaFormDTO.getItensSaida(), percentualDesconto);
            BigDecimal valorTotal = getValorTotal(itensSaida);
            BigDecimal valorDesconto =  getValorDesconto(itensSaida, percentualDesconto);

            notaSaida.setValorTotal(valorTotal);
            notaSaida.setValorDesconto(valorDesconto);
            notaSaida.setPercentualDesconto(percentualDesconto);
            notaSaida.setItensSaida(itensSaida);

            repository.save(notaSaida);

            return mapper.map(notaSaida, NotaSaidaDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_SAVE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    private BigDecimal getValorTotal(Set<ItemSaida> itensSaida) {
        return itensSaida.stream()
                .map(itemSaida -> itemSaida.getValor().multiply(BigDecimal.valueOf(itemSaida.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getValorDesconto(Set<ItemSaida> itensSaida, Double percentualDesconto) {
        Double percent = percentualDesconto / 100;
        return itensSaida.stream()
                .filter(itemSaida -> itemSaida.getValorDesconto() != null)
                .map(itemSaida -> itemSaida.getValor().multiply(BigDecimal.valueOf(percent)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private Set<ItemSaida> getItensSaida(NotaSaida notaSaida, Empresa empresa, Collection<ItemSaidaFormDTO> itemSaidaFormDTOS, Double percentualDesconto) {
        return itemSaidaFormDTOS.stream().map(itemSaidaFormDTO -> {
            Item item = itemService.findItemById(itemSaidaFormDTO.getItemId());
            ItemSaida itemSaida = getItemSaida(notaSaida, item, itemSaidaFormDTO);
            itemSaida.setItem(item);

            ItemEstoque itemEstoque = configurarItemEstoqueNaBase(item, empresa, itemSaida);
            itemSaida.setValor(itemEstoque.getCustoMedio());

            if (itemEstoque.getHasDesconto()) {
                Double percent = percentualDesconto / 100;
                BigDecimal valorDesconto = itemSaida.getValor().multiply(BigDecimal.valueOf(percent));
                itemSaida.setValorDesconto(valorDesconto);
            }

            return itemSaida;
        }).collect(Collectors.toSet());
    }

    private ItemEstoque configurarItemEstoqueNaBase(Item item, Empresa empresa, ItemSaida itemSaida) {
        ItemEstoque itemEstoque = getItemEstoque(item.getId(), empresa.getId(), itemSaida);

        if (!itemEstoque.getIsAtivo()) {
            String message = "Item está desativado, não é possível ter saída";
            LOGGER.error(message);
            throw new BadRequestException(message);
        }

        itemEstoqueService.update(itemEstoque.getId(), itemEstoque);
        return itemEstoque;
    }

    private ItemEstoque getItemEstoque(UUID itemId, UUID empresaId, ItemSaida itemSaida) {
        ItemEstoque itemEstoque = itemEstoqueService.findItemEstoqueByEmpresaAndItem(empresaId, itemId);
        Long totalQuantidade = itemEstoque.getQuantidade() - itemSaida.getQuantidade();
        itemEstoque.setQuantidade(totalQuantidade);
        return itemEstoque;
    }

    private ItemSaida getItemSaida(NotaSaida notaSaida, Item item, ItemSaidaFormDTO itemSaidaFormDTO) {
        ItemSaida itemSaida = new ItemSaida();
        itemSaida.setItem(item);
        itemSaida.setNotaSaida(notaSaida);
        itemSaida.setQuantidade(itemSaidaFormDTO.getQuantidade());
        return itemSaida;
    }

    @Transactional
    public void delete(Long id) {
        try {
            NotaSaida notaSaida = getById(id);
            repository.delete(notaSaida);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    private NotaSaida getById(Long id) {
        String type = getMessage(NAME);
        String message = getMessageFindNotFound(id, type);
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(message));
    }
}