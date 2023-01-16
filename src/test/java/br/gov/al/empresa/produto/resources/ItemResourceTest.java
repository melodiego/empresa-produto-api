package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.buid.BuildItem;
import br.gov.al.empresa.produto.dto.ItemDTO;
import br.gov.al.empresa.produto.dto.form.ItemFormDTO;
import br.gov.al.empresa.produto.services.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemResource.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DisplayName("Item Resource Tests")
public class ItemResourceTest {
    private static final String endpointAPI = "/itens";

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService service;

    @Test
    @DisplayName("Should be return all list items")
    public void shouldFindAll() throws Exception {
        ItemDTO dto = BuildItem.buildDTOBasic();
        when(service.findAll()).thenReturn(Collections.singletonList(dto));
        mockMvc.perform(get(endpointAPI)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).findAll();
    }

    @Test
    @DisplayName("Should be return an item by id")
    public void shouldFindByIdWithAValidIdTest() throws Exception {
        ItemDTO dto = BuildItem.buildDTO();
        UUID id = BuildItem.getID();

        when(service.findById(id)).thenReturn(dto);
        String url = String.format("%s/%s", endpointAPI, id);
        mockMvc.perform(get(url))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(dto.getId().toString()))
                .andExpect(jsonPath("$.nome").value(dto.getNome()))
                .andExpect(jsonPath("$.descricao").value(dto.getDescricao()))
                .andExpect(jsonPath("$.created_at").exists())
                .andExpect(jsonPath("$.updated_at").exists());

        verify(service).findById(id);
    }

    @Test
    @DisplayName("Should be return success when delete with a valid request")
    public void shouldDeleteWithAValidIdTest() throws Exception {
        UUID id = BuildItem.getID();
        String url = String.format("%s/%s", endpointAPI, id);

        mockMvc.perform(delete(url))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}