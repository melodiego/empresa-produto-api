package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.buid.BuildEmpresa;
import br.gov.al.empresa.produto.dto.EmpresaDTO;
import br.gov.al.empresa.produto.dto.form.EmpresaFormDTO;
import br.gov.al.empresa.produto.entity.Empresa;
import br.gov.al.empresa.produto.repository.EmpresaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Empresa Service Tests")
public class EmpresaServiceTest {
    private static final UUID id = UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2");

    @Mock
    private EmpresaRepository repository;
    @Mock
    private NotaEntradaService notaEntradaService;
    @Mock
    private NotaSaidaService notaSaidaService;
    @Mock
    private ItemEstoqueService itemEstoqueService;

    @Mock
    private ModelMapper mapper;
    @Mock
    private ResourceBundleMessageSource messageSource;

    @InjectMocks
    private EmpresaService service;

    @Test
    @DisplayName("Should be return all list empresas")
    public void shouldFindAllTest() {
        when(repository.findAll()).thenReturn(Collections.singletonList(BuildEmpresa.buildEntity()));
        List<EmpresaDTO> dtos = service.findAll();

        assertEquals(dtos.size(), 1);

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should be return an empresa by id")
    public void shouldFindByIdValidTest() {
        Optional<Empresa> entity = Optional.of(BuildEmpresa.buildEntity());
        EmpresaDTO dto = BuildEmpresa.buildDTO();

        when(repository.findById(id)).thenReturn(entity);
        when(mapper.map(any(), eq(EmpresaDTO.class))).thenReturn(dto);

        EmpresaDTO dtoReturned = service.findById(id);

        assertNotNull(dtoReturned);
        assertNotNull(dto.getId());
        assertEquals(dto, dtoReturned);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should be return an dto when save with a valid request")
    public void shouldSaveValidTest() {
        Empresa entity = BuildEmpresa.buildEntity();
        Empresa expected = BuildEmpresa.buildEntity();

        EmpresaFormDTO formDTO = BuildEmpresa.buildFormDTO();
        EmpresaDTO dto = BuildEmpresa.buildDTO();

        when(repository.save(entity)).thenReturn(expected);
        when(mapper.map(any(), eq(Empresa.class))).thenReturn(entity);
        when(mapper.map(any(), eq(EmpresaDTO.class))).thenReturn(dto);

        assertEquals(service.save(formDTO), dto);

        verify(repository).save(eq(entity));
    }

    @Test
    @DisplayName("Should be return a dto when save with a valid request")
    public void shouldUpdateValidTest() {
        Empresa entity = BuildEmpresa.buildEntity();
        Empresa expected = BuildEmpresa.buildEntity();

        EmpresaFormDTO formDTO = BuildEmpresa.buildFormDTO();
        EmpresaDTO dto = BuildEmpresa.buildDTO();

        when(repository.findById(id)).thenReturn(Optional.ofNullable(entity));
        when(repository.save(entity)).thenReturn(expected);
        when(mapper.map(any(), eq(Empresa.class))).thenReturn(entity);
        when(mapper.map(any(), eq(EmpresaDTO.class))).thenReturn(dto);

        assertEquals(service.update(id, formDTO), dto);

        verify(repository).save(eq(entity));
    }
}