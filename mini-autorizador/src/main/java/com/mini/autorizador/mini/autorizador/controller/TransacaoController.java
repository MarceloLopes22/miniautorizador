package com.mini.autorizador.mini.autorizador.controller;

import com.mini.autorizador.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.mini.autorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;


    @PostMapping
    public ResponseEntity save(@RequestBody TransacaoDTO transacaoDTO) {
        ResponseEntity responseEntity = transacaoService.save(transacaoDTO);
        return responseEntity;
    }
}
