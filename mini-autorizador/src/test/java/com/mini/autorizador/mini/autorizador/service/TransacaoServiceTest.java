package com.mini.autorizador.mini.autorizador.service;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.domain.Transacao;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.mini.autorizador.exceptions.CartaoInexistenteException;
import com.mini.autorizador.mini.autorizador.exceptions.SenhaInvalidaException;
import com.mini.autorizador.mini.autorizador.service.impl.CartaoServiceImpl;
import com.mini.autorizador.mini.autorizador.service.impl.TransacaoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TransacaoServiceTest {

    @Mock
    private CartaoServiceImpl cartaoService;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @Test
    @DisplayName("Salvar um cartão no banco de dados com sucesso.")
    public void saveCartao() {

        doReturn(null).when(cartaoService).getCartao(anyString());

        TransacaoDTO transacaoDTO = TransacaoDTO
                .builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("1234")
                .valor(10D)
                .build();

        assertThrows(CartaoInexistenteException.class, ()-> transacaoService.save(transacaoDTO));
    }

    @Test
    @DisplayName("Cartão com senha invalida.")
    public void cartaoComSenhaInvalida() {

        Cartao cartao = Cartao
                .builder()
                .numeroCartao("6549873025634501")
                .senha("6789")
                .saldo(500D)
                .build();

        doReturn(cartao).when(cartaoService).getCartao(anyString());

        TransacaoDTO transacaoDTO = TransacaoDTO
                .builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("1234")
                .valor(10D)
                .build();

        assertThrows(SenhaInvalidaException.class, ()-> transacaoService.save(transacaoDTO));
    }

    @Test
    @DisplayName("Transação salva com sucesso.")
    public void transacaoSalvaComSucesso() {

        Cartao cartao = Cartao
                .builder()
                .numeroCartao("6549873025634501")
                .senha("6789")
                .saldo(500D)
                .build();

        doReturn(cartao).when(cartaoService).getCartao(anyString());

        TransacaoDTO transacaoDTO = TransacaoDTO
                .builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("6789")
                .valor(10D)
                .build();

        ResponseEntity responseEntity = transacaoService.save(transacaoDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(Transacao.class.cast(responseEntity.getBody()));
    }
}
