package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.dto.NotaEntradaDTO;
import br.gov.al.empresa.produto.dto.form.ItemEntradaFormDTO;
import br.gov.al.empresa.produto.dto.form.NotaEntradaFormDTO;
import br.gov.al.empresa.produto.entity.*;
import br.gov.al.empresa.produto.exception.BadRequestException;
import br.gov.al.empresa.produto.exception.RecordNotFoundException;
import br.gov.al.empresa.produto.repository.NotaEntradaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.CODE_DELETE_ERROR;
import static br.gov.al.empresa.produto.util.Constants.GenericConstants.CODE_SAVE_ERROR;

@Service
public class NotaEntradaService extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotaEntradaService.class);
    private static final String NAME = "notaEntrada.name";

    private final NotaEntradaRepository repository;
    private final ItemService itemService;
    private final ItemEstoqueService itemEstoqueService;
    private final EmpresaService empresaService;
    private final ModelMapper mapper;

    @Autowired
    public NotaEntradaService(NotaEntradaRepository repository, ItemService itemService, ItemEstoqueService itemEstoqueService,
                              @Lazy EmpresaService empresaService, ModelMapper mapper, ResourceBundleMessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.itemService = itemService;
        this.itemEstoqueService = itemEstoqueService;
        this.empresaService = empresaService;
        this.mapper = mapper;
    }

    public List<NotaEntradaDTO> findAll() {
        Collection<NotaEntrada> items = repository.findAll();
        return items.stream().map(notaEntrada -> mapper.map(notaEntrada, NotaEntradaDTO.class)).collect(Collectors.toList());
    }

    public List<NotaEntradaDTO> findAllEntradasByEmpresaId(UUID empresaId) {
        Collection<NotaEntrada> items = repository.findByEmpresaId(empresaId);
        return items.stream().map(notaEntrada -> mapper.map(notaEntrada, NotaEntradaDTO.class)).collect(Collectors.toList());
    }

    public NotaEntradaDTO findById(Long id) {
        NotaEntrada notaEntrada = getById(id);
        return mapper.map(notaEntrada, NotaEntradaDTO.class);
    }

    @Transactional
    public NotaEntradaDTO save(NotaEntradaFormDTO notaEntradaFormDTO) {
        try {
            Empresa empresa = empresaService.findEmpresaById(notaEntradaFormDTO.getEmpresaId());

            NotaEntrada notaEntrada = new NotaEntrada();
            notaEntrada.setEmpresa(empresa);
            notaEntrada.setData(notaEntradaFormDTO.getData());

            Set<ItemEntrada> itensEntrada = getItensEntrada(notaEntrada, empresa, notaEntradaFormDTO.getItensEntrada());
            BigDecimal valorTotal = itensEntrada.stream()
                    .map(itemEntrada -> itemEntrada.getValor().multiply(BigDecimal.valueOf(itemEntrada.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            notaEntrada.setValorTotal(valorTotal);
            notaEntrada.setItensEntrada(itensEntrada);
            repository.save(notaEntrada);

            return mapper.map(notaEntrada, NotaEntradaDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_SAVE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    private Set<ItemEntrada> getItensEntrada(NotaEntrada notaEntrada, Empresa empresa, Collection<ItemEntradaFormDTO> itensEntradaFormDTO) {
        return itensEntradaFormDTO.stream().map(itemEntradaFormDTO -> {
            Item item = itemService.findItemById(itemEntradaFormDTO.getItemId());
            ItemEntrada itemEntrada = getItemEntrada(notaEntrada, item, itemEntradaFormDTO);
            itemEntrada.setItem(item);

            configurarItemEstoque(item, empresa, itemEntrada, itemEntradaFormDTO.isDesconto());

            return itemEntrada;
        }).collect(Collectors.toSet());
    }

    private void configurarItemEstoque(Item item, Empresa empresa, ItemEntrada itemEntrada, boolean hasDesconto) {
        boolean existsItemEstoque = itemEstoqueService.existsItemEstoqueByEmpresaIdAndItemId(empresa.getId(), item.getId());
        if (existsItemEstoque) {
            ItemEstoque itemEstoque = getItemEstoque(item, empresa, itemEntrada);
            itemEstoque.setHasDesconto(hasDesconto);
            itemEstoqueService.update(itemEstoque.getId(), itemEstoque);

        } else {
            ItemEstoque itemEstoque = createItemEstoque(item, empresa, itemEntrada);
            itemEstoque.setHasDesconto(hasDesconto);
            itemEstoqueService.save(itemEstoque);
        }
    }

    private ItemEstoque getItemEstoque(Item item, Empresa empresa, ItemEntrada itemEntrada) {
        ItemEstoque itemEstoque = itemEstoqueService.findItemEstoqueByEmpresaAndItem(empresa.getId(), item.getId());
        Long totalQuantidade = Long.sum(itemEstoque.getQuantidade(), itemEntrada.getQuantidade());
        BigDecimal custoMedio = calcularCustoMedio(itemEntrada, itemEstoque, totalQuantidade);

        itemEstoque.setCustoMedio(custoMedio);
        itemEstoque.setQuantidade(totalQuantidade);
        return itemEstoque;
    }

    private BigDecimal calcularCustoMedio(ItemEntrada itemEntrada, ItemEstoque itemEstoque, Long totalQuantidade) {
        BigDecimal valorTotalEntrada = itemEntrada.getValor().multiply(BigDecimal.valueOf(itemEntrada.getQuantidade()));
        BigDecimal valorTotalItemEstoque = itemEstoque.getCustoMedio().multiply(BigDecimal.valueOf(itemEstoque.getQuantidade()));
        BigDecimal valorTotal = valorTotalItemEstoque.add(valorTotalEntrada);

        BigDecimal custoMedio = valorTotal.divide(BigDecimal.valueOf(totalQuantidade), 2, RoundingMode.HALF_UP);
        return custoMedio;
    }

    private ItemEstoque createItemEstoque(Item item, Empresa empresa, ItemEntrada itemEntrada) {
        ItemEstoque itemEstoque = new ItemEstoque();
        itemEstoque.setItem(item);
        itemEstoque.setEmpresa(empresa);
        itemEstoque.setIsAtivo(Boolean.TRUE);
        itemEstoque.setQuantidade(itemEntrada.getQuantidade());
        itemEstoque.setCustoMedio(itemEntrada.getValor());
        return itemEstoque;
    }

    private ItemEntrada getItemEntrada(NotaEntrada notaEntrada, Item item, ItemEntradaFormDTO itemEntradaFormDTO) {
        ItemEntrada itemEntrada = new ItemEntrada();
        itemEntrada.setItem(item);
        itemEntrada.setNotaEntrada(notaEntrada);
        itemEntrada.setQuantidade(itemEntradaFormDTO.getQuantidade());
        itemEntrada.setValor(itemEntradaFormDTO.getValor());
        return itemEntrada;
    }

    @Transactional
    public void delete(Long id) {
        try {
            NotaEntrada notaEntrada = getById(id);
            repository.delete(notaEntrada);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    private NotaEntrada getById(Long id) {
        String type = getMessage(NAME);
        String message = getMessageFindNotFound(id, type);
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(message));
    }
}