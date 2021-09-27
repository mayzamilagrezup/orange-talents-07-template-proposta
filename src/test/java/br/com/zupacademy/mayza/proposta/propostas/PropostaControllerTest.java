package br.com.zupacademy.mayza.proposta.propostas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class PropostaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropostaRepository repository;

    @Test
    void deveCadastrarNovaProposta() throws Exception {

        NovaPropostaRequest body = body("58034724092", "teste@gmail.com", "1000.0");

        MockHttpServletRequestBuilder request = postRequest(body);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void naoDeveCadastrarNovaPropostaComDocumentoInvalido() throws Exception {

        NovaPropostaRequest body = body("11111111111", "teste@gmail.com", "1000.0");

        MockHttpServletRequestBuilder request = postRequest(body);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void naoDeveCadastrarNovaPropostaComEmailEmFormatoInvalido() throws Exception {

        NovaPropostaRequest body = body("58034724092", "testegmail.com", "1000.0");

        MockHttpServletRequestBuilder request = postRequest(body);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void naoDeveCadastrarNovaPropostaComSalarioNegativo() throws Exception {

        NovaPropostaRequest body = body("58034724092", "teste@gmail.com", "-1000.0");

        MockHttpServletRequestBuilder request = postRequest(body);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void naoDeveCadastrarNovaPropostaComDadosNulos() throws Exception {

        NovaPropostaRequest body = new NovaPropostaRequest(null, null,
                null,null, null, null, null, null,
                null, null);

        MockHttpServletRequestBuilder request = postRequest(body);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarPropostaComDocumentosIguais() throws Exception {

        NovaPropostaRequest body = body("58034724092", "teste@gmail.com", "1000.0");
        MockHttpServletRequestBuilder request = postRequest(body);
        mvc.perform(request)
                .andExpect(status().isCreated());

        NovaPropostaRequest body2 = body("58034724092", "teste2@gmail.com", "2000.0");
        MockHttpServletRequestBuilder request2 = postRequest(body2);
        mvc.perform(request2)
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void deveRetornarOsDetalhesDaPropostasQuandoInformadoUmIdValido() throws Exception {

        NovaPropostaRequest body = body("58034724092", "teste@gmail.com", "1000.0");

        MockHttpServletRequestBuilder request = postRequest(body);

        Proposta proposta = repository.save(body.toProposta());
        Long id = proposta.getId();

        MockHttpServletRequestBuilder response = get("/propostas/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> new DetalhesDaPropostaResponse(proposta));

    }

    @Test
    void naoDeveRetornarOsDetalhesDaPropostaQuandoInformadoUmIdInvalido() throws Exception {

        MockHttpServletRequestBuilder response = get("/propostas/2")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(response)
                .andExpect(status().isNotFound());

    }

    private NovaPropostaRequest body(String documento, String email, String salario) {
        return new NovaPropostaRequest(
                documento, email,"Maria","58079240", "Rua A", "10", "A",
                "Rio","RJ", new BigDecimal(salario));
    }

    private MockHttpServletRequestBuilder postRequest(NovaPropostaRequest body) throws JsonProcessingException {
        return MockMvcRequestBuilders.post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(body));
    }

}