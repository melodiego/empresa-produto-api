package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.dto.ItemDTO;
import br.gov.al.empresa.produto.dto.form.ItemFormDTO;
import br.gov.al.empresa.produto.entity.Item;
import br.gov.al.empresa.produto.entity.TipoItem;
import br.gov.al.empresa.produto.exception.BadRequestException;
import br.gov.al.empresa.produto.exception.RecordNotFoundException;
import br.gov.al.empresa.produto.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.*;

@Service
public class ItemService extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private static final String NAME = "item.name";

    private final ItemRepository repository;
    private final TipoItemService tipoItemService;
    private final ItemEstoqueService itemEstoqueService;
    private final ModelMapper mapper;

    @Autowired
    public ItemService(ItemRepository repository, TipoItemService tipoItemService, ItemEstoqueService itemEstoqueService, ModelMapper mapper, ResourceBundleMessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.tipoItemService = tipoItemService;
        this.itemEstoqueService = itemEstoqueService;
        this.mapper = mapper;
    }

    public List<ItemDTO> findAll() {
        Collection<Item> items = repository.findAll();
        return items.stream().map(item -> mapper.map(item, ItemDTO.class)).collect(Collectors.toList());
    }

    public ItemDTO findById(UUID id) {
        Item item = getById(id);
        return mapper.map(item, ItemDTO.class);
    }

    public Item findItemById(UUID id) {
        return getById(id);
    }

    @Transactional
    public ItemDTO save(ItemFormDTO itemFormDTO) {
        try {
            Item item = mapper.map(itemFormDTO, Item.class);
            TipoItem tipoItem = tipoItemService.findTipoById(itemFormDTO.getTipoId());

            item.setCodigo(getNextCodigo());
            item.setTipoItem(tipoItem);
            repository.save(item);

            return mapper.map(item, ItemDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_SAVE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public ItemDTO update(UUID id, ItemFormDTO itemFormDTO) {
        try {
            Item itemSaved = getById(id);
            Item item = mapper.map(itemFormDTO, Item.class);
            TipoItem tipoItem = tipoItemService.findTipoById(itemFormDTO.getTipoId());

            item.setId(id);
            item.setTipoItem(tipoItem);
            item.setCreatedAt(itemSaved.getCreatedAt());
            item.setCodigo(itemSaved.getCodigo());

            repository.save(item);

            return mapper.map(item, ItemDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_UPDATE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public void delete(UUID id) {
        try {
            Item item = getById(id);

            if (itemEstoqueService.existsItemEstoqueByItemId(item.getId())) {
                String message = "Não é possível excluir, existem associações";
                LOGGER.error(message);
                throw new BadRequestException(message);
            }

            repository.delete(item);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    public Page<ItemDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Item> produtosPage = repository.findAll(pageRequest);

        return convertToPageDTO(produtosPage);
    }

    private Page<ItemDTO> convertToPageDTO(Page<Item> produtos) {
        return produtos.map(new Function<Item, ItemDTO>() {
            @Override
            public ItemDTO apply(Item item) {
                return mapper.map(item, ItemDTO.class);
            }
        });
    }

    private Item getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> {
            String type = getMessage(NAME);
            String message = getMessageFindNotFound(id, type);
            return new RecordNotFoundException(message);
        });
    }

    private Long getNextCodigo() {
        Long maxCodigo = repository.getMaxCodigo();
        return maxCodigo == null ? 1L : Long.sum(1, maxCodigo);
    }
}