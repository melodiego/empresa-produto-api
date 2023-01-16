package br.gov.al.empresa.produto.services;

import br.gov.al.empresa.produto.buid.BuildItem;
import br.gov.al.empresa.produto.buid.BuildTipoItem;
import br.gov.al.empresa.produto.dto.ItemDTO;
import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.ItemFormDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.entity.Item;
import br.gov.al.empresa.produto.entity.TipoItem;
import br.gov.al.empresa.produto.repository.ItemRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Item Service Tests")
public class ItemServiceTest {
    private static final UUID id = UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2");

    @Mock
    private ItemRepository repository;
    @Mock
    private TipoItemService tipoItemService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private ResourceBundleMessageSource messageSource;

    @InjectMocks
    private ItemService service;

    @Test
    @DisplayName("Should be return all list itens")
    public void shouldFindAllTest() {
        when(repository.findAll()).thenReturn(Collections.singletonList(BuildItem.buildEntity()));
        List<ItemDTO> dtos = service.findAll();
        assertEquals(dtos.size(), 1);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should be return an item by id")
    public void shouldFindByIdValidTest() {
        Optional<Item> entity = Optional.of(BuildItem.buildEntity());
        ItemDTO dto = BuildItem.buildDTO();

        when(repository.findById(id)).thenReturn(entity);
        when(mapper.map(any(), eq(ItemDTO.class))).thenReturn(dto);

        ItemDTO dtoSaved = service.findById(id);

        assertNotNull(dtoSaved);
        assertNotNull(dto.getId());
        assertEquals(dto, dtoSaved);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should be return a dto when save with a valid request")
    public void shouldSaveValidTest() {
        Item entity = BuildItem.buildEntity();
        Item expected = BuildItem.buildEntity();

        TipoItem tipoItem = BuildTipoItem.buildEntity();
        ItemFormDTO formDTO = BuildItem.buildFormDTO();

        ItemDTO dto = BuildItem.buildDTO();

        when(tipoItemService.findTipoById(1L)).thenReturn(tipoItem);
        when(repository.save(entity)).thenReturn(expected);
        when(mapper.map(any(), eq(Item.class))).thenReturn(entity);
        when(mapper.map(any(), eq(ItemDTO.class))).thenReturn(dto);

        assertEquals(service.save(formDTO), dto);

        verify(repository).save(eq(entity));
    }

    @Test
    @DisplayName("Should be return a dto when save with a valid request")
    public void shouldUpdateValidTest() {
        Item entity = BuildItem.buildEntity();
        Item expected = BuildItem.buildEntity();

        ItemFormDTO formDTO = BuildItem.buildFormDTO();
        ItemDTO dto = BuildItem.buildDTO();

        when(repository.findById(id)).thenReturn(Optional.ofNullable(entity));
        when(repository.save(entity)).thenReturn(expected);
        when(mapper.map(any(), eq(Item.class))).thenReturn(entity);
        when(mapper.map(any(), eq(ItemDTO.class))).thenReturn(dto);

        assertEquals(service.update(id, formDTO), dto);

        verify(repository).save(eq(entity));
    }
}