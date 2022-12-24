package com.mini.autorizador.mini.autorizador.service;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import org.springframework.http.ResponseEntity;

public interface CartaoService {
    ResponseEntity save(CartaoDTO cartaoDTO);

    ResponseEntity getSaldo(String numCartao);

    Cartao getCartao(String numCartao);

    ResponseEntity validarSaldo(Cartao cartao);
}
