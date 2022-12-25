package com.mini.autorizador.mini.autorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.mini.autorizador.service.CartaoService;
import com.mini.autorizador.mini.autorizador.service.TransacaoService;
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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class TransacaoControllerTest {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Transacao com senha invalida.")
    public void SenhaInvalidaException() throws Exception {

        CartaoDTO cartaoDTO = CartaoDTO
                .builder()
                .numeroCartao("123456789")
                .senha("5")
                .build();

        cartaoService.save(cartaoDTO);

        TransacaoDTO transacaoDTO = TransacaoDTO
                .builder()
                .numeroCartao("123456789")
                .senhaCartao("987")
                .valor(100.0D)
                .build();

        mockMvc.perform(post("/v1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transacaoDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("Senha invalida")));
    }

    @Test
    @DisplayName("Criar uma transacao")
    public void criarTransacao() throws Exception {

        CartaoDTO cartaoDTO = CartaoDTO
                .builder()
                .numeroCartao("123456789")
                .senha("1234")
                .build();

        cartaoService.save(cartaoDTO);

        TransacaoDTO transacaoDTO = TransacaoDTO
                .builder()
                .numeroCartao("123456789")
                .senhaCartao("1234")
                .valor(100.0D)
                .build();

        mockMvc.perform(post("/v1/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transacaoDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.senhaCartao", is("1234")))
                .andExpect(jsonPath("$.valor", is(100.0D)))
                .andExpect(jsonPath("$.cartao.numeroCartao", is("123456789")))
                .andExpect(jsonPath("$.cartao.senha", is("1234")))
                .andExpect(jsonPath("$.cartao.saldo", is(400.0D)));
    }
}
