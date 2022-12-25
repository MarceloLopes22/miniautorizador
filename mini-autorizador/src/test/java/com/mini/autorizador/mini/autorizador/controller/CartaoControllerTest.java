package com.mini.autorizador.mini.autorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.service.impl.CartaoServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CartaoControllerTest {

    @Autowired
    private CartaoServiceImpl cartaoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("O cartão foi salvo com sucesso.")
    public void cartaoCadastradoComSucesso() throws Exception {

        CartaoDTO cartaoDTO = CartaoDTO
                .builder()
                .numeroCartao("123456")
                .senha("1")
                .build();

        mockMvc.perform(post("/v1/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cartaoDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCartao", Matchers.is("123456")))
                .andExpect(jsonPath("$.senha", Matchers.is("1")))
                .andExpect(jsonPath("$.saldo", Matchers.is(500D)));
    }

    @Test
    @DisplayName("Numero de cartão não encontrado.")
    public void cartaoNaoEncontrado() throws Exception {

        createCartaoNaoEncontrado();

        mockMvc.perform(get("/v1/cartoes/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Cartão encontrado.")
    public void cartaoEncontrado() throws Exception {

        createCartaoEncontrado();

        mockMvc.perform(get("/v1/cartoes/123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(500.0D)));
    }

    private void createCartaoNaoEncontrado() {
        CartaoDTO cartaoDTO = CartaoDTO
                .builder()
                .numeroCartao("123456789")
                .senha("123456")
                .build();

        cartaoService.save(cartaoDTO);
    }

    private void createCartaoEncontrado() {
        CartaoDTO cartaoDTO = CartaoDTO
                .builder()
                .numeroCartao("123456")
                .senha("123456")
                .build();

        cartaoService.save(cartaoDTO);
    }
}
