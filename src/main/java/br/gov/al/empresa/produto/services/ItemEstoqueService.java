package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.dto.ItemEstoqueDTO;
import br.gov.al.empresa.produto.entity.ItemEstoque;
import br.gov.al.empresa.produto.exception.BadRequestException;
import br.gov.al.empresa.produto.exception.RecordNotFoundException;
import br.gov.al.empresa.produto.repository.ItemEstoqueRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.*;

@Service
public class ItemEstoqueService extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemEstoqueService.class);
    private static final String NAME = "itemEstoque.name";

    private final ItemEstoqueRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ItemEstoqueService(ItemEstoqueRepository repository, ModelMapper mapper, ResourceBundleMessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ItemEstoqueDTO> findAll() {
        Collection<ItemEstoque> itemEstoque = repository.findAll(Sort.by("item.nome"));
        return itemEstoque.stream().map(estoque -> mapper.map(estoque, ItemEstoqueDTO.class)).collect(Collectors.toList());
    }

    public ItemEstoqueDTO findById(Long id) {
        ItemEstoque itemEstoque = getById(id);
        return mapper.map(itemEstoque, ItemEstoqueDTO.class);
    }

    public List<ItemEstoqueDTO> findAllItensEstoqueByEmpresaId(UUID empresaId) {
        Collection<ItemEstoque> items = repository.findByEmpresaId(empresaId);
        return items.stream().map(itemEstoque -> mapper.map(itemEstoque, ItemEstoqueDTO.class)).collect(Collectors.toList());
    }

    public ItemEstoque findItemEstoqueByEmpresaAndItem(UUID empresaId, UUID itemId) {
        return repository.findByEmpresaIdAndItemId(empresaId, itemId).orElse(null);
    }

    public boolean existsItemEstoqueByEmpresaIdAndItemId(UUID empresaId, UUID itemId) {
        return repository.findByEmpresaIdAndItemId(empresaId, itemId).isPresent();
    }

    public boolean existsItemEstoqueByItemId(UUID itemId) {
        return repository.findByItemId(itemId).isPresent();
    }

    @Transactional
    public ItemEstoqueDTO save(ItemEstoque itemEstoque) {
        try {
            repository.save(itemEstoque);

            return mapper.map(itemEstoque, ItemEstoqueDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_SAVE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public ItemEstoqueDTO update(Long id, ItemEstoque itemEstoque) {
        try {
            LocalDateTime createdAt = getById(id).getCreatedAt();
            itemEstoque.setCreatedAt(createdAt);
            itemEstoque.setId(id);
            repository.save(itemEstoque);

            return mapper.map(itemEstoque, ItemEstoqueDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_UPDATE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            ItemEstoque itemEstoque = getById(id);
            repository.delete(itemEstoque);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public void ativar(UUID empresaId, UUID itemId) {
        try {
            ItemEstoque itemEstoque = findItemEstoqueByEmpresaAndItem(empresaId, itemId);
            itemEstoque.setIsAtivo(Boolean.TRUE);
            repository.save(itemEstoque);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public void desativar(UUID empresaId, UUID itemId) {
        try {
            ItemEstoque itemEstoque = findByEmpresaIdAndItemId(empresaId, itemId);
            itemEstoque.setIsAtivo(Boolean.FALSE);
            repository.save(itemEstoque);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    private ItemEstoque findByEmpresaIdAndItemId(UUID empresaId, UUID itemId) {
        String type = getMessage(NAME);
        String message = getMessageFindNotFound(itemId, type);
        return repository.findByEmpresaIdAndItemId(empresaId, itemId).orElseThrow(() -> new RecordNotFoundException(message));
    }


    private ItemEstoque getById(Long id) {
        String type = getMessage(NAME);
        String message = getMessageFindNotFound(id, type);
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(message));
    }
}