package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.dto.EmpresaDTO;
import br.gov.al.empresa.produto.dto.ItemEstoqueDTO;
import br.gov.al.empresa.produto.dto.NotaEntradaDTO;
import br.gov.al.empresa.produto.dto.NotaSaidaDTO;
import br.gov.al.empresa.produto.dto.form.EmpresaFormDTO;
import br.gov.al.empresa.produto.entity.Empresa;
import br.gov.al.empresa.produto.exception.BadRequestException;
import br.gov.al.empresa.produto.exception.RecordNotFoundException;
import br.gov.al.empresa.produto.repository.EmpresaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.*;

@Service
public class EmpresaService extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaService.class);
    private static final String NAME = "empresa.name";

    private final EmpresaRepository repository;
    private final NotaEntradaService notaEntradaService;
    private final NotaSaidaService notaSaidaService;
    private final ItemEstoqueService itemEstoqueService;
    private final ModelMapper mapper;

    @Autowired
    public EmpresaService(EmpresaRepository repository, NotaEntradaService notaEntradaService,
                          NotaSaidaService notaSaidaService, ItemEstoqueService itemEstoqueService, ModelMapper mapper, ResourceBundleMessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.notaEntradaService = notaEntradaService;
        this.notaSaidaService = notaSaidaService;
        this.itemEstoqueService = itemEstoqueService;
        this.mapper = mapper;
    }

    public List<EmpresaDTO> findAll() {
        Collection<Empresa> entradas = repository.findAll();
        return entradas.stream().map(empresa -> mapper.map(empresa, EmpresaDTO.class)).collect(Collectors.toList());
    }

    public EmpresaDTO findById(UUID id) {
        Empresa empresa = getById(id);
        return mapper.map(empresa, EmpresaDTO.class);
    }

    public Empresa findEmpresaById(UUID id) {
        return getById(id);
    }

    public List<NotaEntradaDTO> findAllEntradasByEmpresaId(UUID empresaId) {
        return notaEntradaService.findAllEntradasByEmpresaId(empresaId);
    }

    public List<NotaSaidaDTO> findAllSaidasByEmpresaId(UUID empresaId) {
        return notaSaidaService.findAllSaidasByEmpresaId(empresaId);
    }

    public List<ItemEstoqueDTO> findAllItensEstoqueByEmpresaId(UUID empresaId) {
        return itemEstoqueService.findAllItensEstoqueByEmpresaId(empresaId);
    }

    @Transactional
    public EmpresaDTO save(EmpresaFormDTO empresaFormDTO) {
        try {
            Empresa empresa = mapper.map(empresaFormDTO, Empresa.class);
            repository.save(empresa);

            return mapper.map(empresa, EmpresaDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_SAVE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }


    public EmpresaDTO update(UUID id, EmpresaFormDTO empresaFormDTO) {
        try {
            Empresa empresaSalva = getById(id);
            Empresa empresa = mapper.map(empresaFormDTO, Empresa.class);
            empresa.setCreatedAt(empresaSalva.getCreatedAt());
            empresa.setId(id);
            repository.save(empresa);

            return mapper.map(empresa, EmpresaDTO.class);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_UPDATE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    public void delete(UUID id) {
        try {
            Empresa empresa = getById(id);
            repository.delete(empresa);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    public void ativarItemEstoque(UUID empresaId, UUID id) {
        try {
            itemEstoqueService.ativar(empresaId, id);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    public void desativarItemEstoque(UUID empresaId, UUID id) {
        try {
            itemEstoqueService.desativar(empresaId, id);
        } catch (Exception exception) {
            String message = getMessageFullError(CODE_DELETE_ERROR, exception);
            LOGGER.error(message);
            throw new BadRequestException(message);
        }
    }

    private Empresa getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> {
            String type = getMessage(NAME);
            String message = getMessageFindNotFound(id, type);
            return new RecordNotFoundException(message);
        });
    }
}