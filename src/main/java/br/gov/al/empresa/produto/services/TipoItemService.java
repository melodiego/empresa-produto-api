package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.entity.TipoItem;
import br.gov.al.empresa.produto.exception.BadRequestException;
import br.gov.al.empresa.produto.exception.RecordNotFoundException;
import br.gov.al.empresa.produto.repository.TipoItemRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.*;

@Service
public class TipoItemService extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TipoItemService.class);
    private static final String NAME = "tipoItem.name";

    private final TipoItemRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public TipoItemService(TipoItemRepository repository, ModelMapper mapper, ResourceBundleMessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TipoItemDTO> findAll() {
        Collection<TipoItem> tipoItems = repository.findAll();
        return tipoItems.stream().map(tipoItem -> mapper.map(tipoItem, TipoItemDTO.class)).collect(Collectors.toList());
    }

    public TipoItemDTO findById(Long id) {
        TipoItem tipoItem = getById(id);
        return mapper.map(tipoItem, TipoItemDTO.class);
    }

    public TipoItem findTipoById(Long id) {
        return getById(id);
    }

    @Transactional
    public TipoItemDTO save(TipoItemFormDTO tipoItemFormDTO) {
        try {
            TipoItem tipoItem = mapper.map(tipoItemFormDTO, TipoItem.class);
            repository.save(tipoItem);

            return mapper.map(tipoItem, TipoItemDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_SAVE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public TipoItemDTO update(Long id, TipoItemFormDTO tipoItemFormDTO) {
        try {
            TipoItem tipoItemSalvo = getById(id);
            TipoItem tipoItem = mapper.map(tipoItemFormDTO, TipoItem.class);
            tipoItem.setCreatedAt(tipoItemSalvo.getCreatedAt());
            tipoItem.setId(id);
            repository.save(tipoItem);

            return mapper.map(tipoItem, TipoItemDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_UPDATE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            TipoItem tipoItem = getById(id);
            repository.delete(tipoItem);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new br.gov.al.empresa.produto.exception.BadRequestException(message);
        }
    }

    private TipoItem getById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            String type = getMessage(NAME);
            String message = getMessageFindNotFound(id, type);
            return new RecordNotFoundException(message);
        });
    }
}