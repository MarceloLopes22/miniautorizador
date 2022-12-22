package com.mini.autorizador.mini.autorizador.service;

import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import org.springframework.http.ResponseEntity;

public interface CartaoService {
    ResponseEntity save(CartaoDTO cartaoDTO);

    ResponseEntity get(String numCartao);
}
