package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.buid.BuildTipoItem;
import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.entity.TipoItem;
import br.gov.al.empresa.produto.repository.TipoItemRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tipo Item Service Tests")
public class TipoItemServiceTest {
    private static final Long id = 1L;

    @Mock
    private TipoItemRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private ResourceBundleMessageSource messageSource;

    @InjectMocks
    private TipoItemService service;

    @Test
    @DisplayName("Should be return all list tipo de itens")
    public void shouldFindAllTest() {
        when(repository.findAll()).thenReturn(Collections.singletonList(BuildTipoItem.buildEntity()));
        List<TipoItemDTO> dtos = service.findAll();
        assertEquals(dtos.size(), 1);
        verify(repository).findAll();
    }

    @Test
    public void shouldFindByIdValidTest() {
        Optional<TipoItem> entity = Optional.of(BuildTipoItem.buildEntity());
        TipoItemDTO dto = BuildTipoItem.buildDTO();

        when(repository.findById(id)).thenReturn(entity);
        when(mapper.map(any(), eq(TipoItemDTO.class))).thenReturn(dto);

        TipoItemDTO itemDTOSaved = service.findById(id);

        assertNotNull(itemDTOSaved);
        assertNotNull(dto.getId());
        assertEquals(dto, itemDTOSaved);

        verify(repository).findById(id);
    }

    @Test
    public void shouldSaveValidTest() {
        TipoItem entity = BuildTipoItem.buildEntity();
        TipoItem expected = BuildTipoItem.buildEntity();

        TipoItemFormDTO formDTO = BuildTipoItem.buildFormDTO();
        TipoItemDTO dto = BuildTipoItem.buildDTO();

        when(repository.save(entity)).thenReturn(expected);
        when(mapper.map(any(), eq(TipoItem.class))).thenReturn(entity);
        when(mapper.map(any(), eq(TipoItemDTO.class))).thenReturn(dto);

        assertEquals(service.save(formDTO), dto);

        verify(repository).save(eq(entity));
    }

    @Test
    public void shouldUpdateValidTest() {
        TipoItem entity = BuildTipoItem.buildEntity();
        TipoItem expected = BuildTipoItem.buildEntity();

        TipoItemFormDTO formDTO = BuildTipoItem.buildFormDTO();
        TipoItemDTO dto = BuildTipoItem.buildDTO();

        when(repository.findById(id)).thenReturn(Optional.ofNullable(entity));
        when(repository.save(entity)).thenReturn(expected);
        when(mapper.map(any(), eq(TipoItem.class))).thenReturn(entity);
        when(mapper.map(any(), eq(TipoItemDTO.class))).thenReturn(dto);

        assertEquals(service.update(id, formDTO), dto);

        verify(repository).save(eq(entity));
    }
}