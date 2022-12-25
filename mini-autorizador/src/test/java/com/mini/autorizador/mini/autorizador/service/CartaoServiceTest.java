package com.mini.autorizador.mini.autorizador.service;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.exceptions.SaldoInsuficienteException;
import com.mini.autorizador.mini.autorizador.service.impl.CartaoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartaoServiceTest {

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    @Test
    @DisplayName("Salvar um cartão no banco de dados com sucesso.")
    public void saveCartao() {

        CartaoDTO cartaoDTO = CartaoDTO
                .builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build();
        ResponseEntity responseEntity = cartaoService.save(cartaoDTO);

        Assertions.assertNotNull(Cartao.class.cast(responseEntity.getBody()));
    }

    @Test
    @DisplayName("Cartão com saldo invalido.")
    public void saldoInvalido() {

        Cartao cartao = Cartao
                .builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .saldo(Double.valueOf("0"))
                .build();

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class,
                () -> cartaoService.validarSaldo(cartao));
        assertEquals("Saldo Insuficiente", exception.getMessage());
    }

    @Test
    @DisplayName("Cartão com saldo valido.")
    public void saldoValido() {

        Cartao cartao = Cartao
                .builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .saldo(Double.valueOf("100"))
                .build();

        cartaoService.validarSaldo(cartao);
    }

    @Test
    @DisplayName("Cartão sem saldo.")
    public void semSaldo() {

        ResponseEntity responseEntity = cartaoService.getSaldo("6549873025634501");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, Double.class.cast(responseEntity.getBody()));
    }

    @Test
    @DisplayName("Cartão com saldo.")
    public void comSaldo() {

        cartaoService.save(CartaoDTO.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build());

        ResponseEntity responseEntity = cartaoService.getSaldo("6549873025634501");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Double.class.cast(responseEntity.getBody()));
    }

    @Test
    @DisplayName("Recuperando o cartão.")
    public void recuperandoCartao() {

        cartaoService.save(CartaoDTO.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build());

        Cartao cartao = cartaoService.getCartao("6549873025634501");

        assertNotNull(cartao);
    }

    @Test
    @DisplayName("Cartão não existe.")
    public void cartaoNaoExiste() {

        cartaoService.save(CartaoDTO.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build());

        Cartao cartao = cartaoService.getCartao("6549873025634555");

        assertNull(cartao);
    }
}
