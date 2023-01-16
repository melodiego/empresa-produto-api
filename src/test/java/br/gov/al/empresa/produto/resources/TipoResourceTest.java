package br.gov.al.empresa.produto.resources;


import br.gov.al.empresa.produto.buid.BuildTipoItem;
import br.gov.al.empresa.produto.dto.TipoItemDTO;
import br.gov.al.empresa.produto.dto.form.TipoItemFormDTO;
import br.gov.al.empresa.produto.services.TipoItemService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TipoItemResource.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DisplayName("Tipo Item Resource Tests")
public class TipoResourceTest {

    private static final String endpointAPI = "/tipo-itens";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TipoItemService service;

    @Test
    @DisplayName("Should be return all list tipo de itens")
    public void shouldFindAll() throws Exception {
        TipoItemDTO tipoItemDTO = BuildTipoItem.buildDTOBasic();
        when(service.findAll()).thenReturn(Collections.singletonList(tipoItemDTO));
        mockMvc.perform(
                        get(endpointAPI).content(mapper.writeValueAsString(tipoItemDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(service).findAll();
    }

    @Test
    @DisplayName("Should be return an item by id")
    public void shouldFindByIdWithAValidIdTest() throws Exception {
        TipoItemDTO tipoItemDTO = BuildTipoItem.buildDTO();
        when(service.findById(anyLong())).thenReturn(tipoItemDTO);
        Long id = 1L;

        mockMvc.perform(get((String.format("%s/%s", endpointAPI, id))))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").value(tipoItemDTO.getId()))
                .andExpect(jsonPath("$.nome").value(tipoItemDTO.getNome()))
                .andExpect(jsonPath("$.descricao").value(tipoItemDTO.getDescricao()))
                .andExpect(jsonPath("$.created_at").exists())
                .andExpect(jsonPath("$.updated_at").exists());

        verify(service).findById(id);
    }

    @Test
    @DisplayName("Should be return a dto when save with a valid request")
    public void shouldSavedWithAValidRequestTest() throws Exception {
        TipoItemDTO tipoItemDTO = BuildTipoItem.buildDTO();
        TipoItemFormDTO tipoItemFormDTO = BuildTipoItem.buildFormDTO();

        when(service.save(any())).thenReturn(tipoItemDTO);

        mockMvc.perform(
                        post(endpointAPI).content(mapper.writeValueAsString(tipoItemFormDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").value(tipoItemDTO.getId()))
                .andExpect(jsonPath("$.nome").value(tipoItemDTO.getNome()))
                .andExpect(jsonPath("$.descricao").value(tipoItemDTO.getDescricao()))
                .andExpect(jsonPath("$.created_at").exists())
                .andExpect(jsonPath("$.updated_at").exists());

        verify(service).save(eq(tipoItemFormDTO));
    }

    @Test
    @DisplayName("Should be return a dto when update with a valid request")
    public void shouldUpdateWithAValidRequestTest() throws Exception {
        TipoItemDTO tipoItemDTO = BuildTipoItem.buildDTO();
        TipoItemFormDTO tipoItemFormDTO = BuildTipoItem.buildFormDTO();
        when(service.update(anyLong(), eq(tipoItemFormDTO))).thenReturn(tipoItemDTO);
        mockMvc.perform(put((String.format("%s/%s", endpointAPI, 1L)))
                        .content(mapper.writeValueAsString(tipoItemFormDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").value(tipoItemDTO.getId()))
                .andExpect(jsonPath("$.nome").value(tipoItemDTO.getNome()))
                .andExpect(jsonPath("$.descricao").value(tipoItemDTO.getDescricao()))
                .andExpect(jsonPath("$.created_at").exists())
                .andExpect(jsonPath("$.updated_at").exists());

        verify(service).update(anyLong(), eq(tipoItemFormDTO));
    }

    @Test
    @DisplayName("Should be return success when delete with a valid request")
    public void shouldDeleteWithAValidIdTest() throws Exception {
        mockMvc.perform(delete((String.format("%s/%s", endpointAPI, 1L))))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }


}
