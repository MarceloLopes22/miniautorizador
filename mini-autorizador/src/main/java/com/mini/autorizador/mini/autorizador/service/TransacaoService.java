package com.mini.autorizador.mini.autorizador.service;

import com.mini.autorizador.mini.autorizador.dto.TransacaoDTO;
import org.springframework.http.ResponseEntity;

public interface TransacaoService {
    ResponseEntity save(TransacaoDTO transacaoDTO);
}
