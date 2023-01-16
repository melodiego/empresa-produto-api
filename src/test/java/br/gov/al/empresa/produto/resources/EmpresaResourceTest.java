package br.gov.al.empresa.produto.resources;

import br.gov.al.empresa.produto.buid.BuildEmpresa;
import br.gov.al.empresa.produto.dto.EmpresaDTO;
import br.gov.al.empresa.produto.services.EmpresaService;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmpresaResource.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DisplayName("Empresa Resource Tests")
public class EmpresaResourceTest {

    private static final String endpointAPI = "/empresas";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmpresaService service;

    @Test
    @DisplayName("Should be return all list empresas")
    public void shouldFindAll() throws Exception {
        EmpresaDTO empresaDTO = BuildEmpresa.buildDTOBasic();
        when(service.findAll()).thenReturn(Collections.singletonList(empresaDTO));
        mockMvc.perform(get(endpointAPI)
                        .content(mapper.writeValueAsString(empresaDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(service).findAll();
    }

    @Test
    @DisplayName("Should be return an item by id")
    public void shouldFindByIdWithAValidIdTest() throws Exception {
        EmpresaDTO empresaDTO = BuildEmpresa.buildDTO();
        when(service.findById(any())).thenReturn(empresaDTO);
        UUID id = UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2");

        mockMvc.perform(get((String.format("%s/%s", endpointAPI, id))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(empresaDTO.getId().toString()))
                .andExpect(jsonPath("$.nome").value(empresaDTO.getNome()))
                .andExpect(jsonPath("$.cnpj").value(empresaDTO.getCnpj()))
                .andExpect(jsonPath("$.created_at").exists())
                .andExpect(jsonPath("$.updated_at").exists())
                .andExpect(jsonPath("$.entradas").isEmpty())
                .andExpect(jsonPath("$.saidas").isEmpty());

        verify(service).findById(id);
    }

    @Test
    @DisplayName("Should be return success when delete with a valid request")
    public void shouldDeleteWithAValidIdTest() throws Exception {
        UUID id = UUID.fromString("df719c9d-8b8b-4715-8577-5a33f63f44f2");
        mockMvc.perform(delete((String.format("%s/%s", endpointAPI, id))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
