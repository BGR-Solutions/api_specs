package com.example.familyfinance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.familyfinance.application.dto.CategoriaRequest;
import com.example.familyfinance.application.dto.DespesaRequest;
import com.example.familyfinance.application.dto.MembroFamiliaRequest;
import com.example.familyfinance.domain.enums.TipoCategoria;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DespesaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID membroId;
    private UUID categoriaId;

    @BeforeEach
    void setup() throws Exception {
        MembroFamiliaRequest membroRequest = new MembroFamiliaRequest();
        membroRequest.setNome("Maria");
        membroRequest.setRelacao("Mae");

        String membroResponse = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(membroRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        membroId = UUID.fromString(objectMapper.readTree(membroResponse).get("id").asText());

        CategoriaRequest categoriaRequest = new CategoriaRequest();
        categoriaRequest.setNome("Mercado");
        categoriaRequest.setTipo(TipoCategoria.DESPESA);

        String categoriaResponse = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        categoriaId = UUID.fromString(objectMapper.readTree(categoriaResponse).get("id").asText());
    }

    @Test
    void deveCriarDespesaComMembroResponsavel() throws Exception {
        DespesaRequest request = new DespesaRequest();
        request.setCategoriaId(categoriaId);
        request.setMembroFamiliaId(membroId);
        request.setDescricao("Compras do mercado");
        request.setValor(new BigDecimal("420.00"));
        request.setDataOcorrencia(LocalDate.now());
        request.setObservacoes("Mensal");

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Compras do mercado"))
                .andExpect(jsonPath("$.membroFamiliaId").value(membroId.toString()))
                .andExpect(jsonPath("$.categoriaId").value(categoriaId.toString()));
    }

    @Test
    void deveListarDespesas() throws Exception {
        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk());
    }
}
