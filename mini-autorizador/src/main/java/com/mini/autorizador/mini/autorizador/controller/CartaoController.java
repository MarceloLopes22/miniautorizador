package com.mini.autorizador.mini.autorizador.controller;

import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity save(@RequestBody CartaoDTO cartaoDTO) {
        ResponseEntity responseEntity = cartaoService.save(cartaoDTO);
        return responseEntity;
    }

    @GetMapping(value = "/{numeroCartao}")//Não to conseguindo pegar o parametro do get
    public ResponseEntity getSaldo(@PathVariable("numeroCartao") String numeroCartao) {
        return cartaoService.getSaldo(numeroCartao);
    }
}
