package com.mini.autorizador.mini.autorizador.service.impl;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.domain.Transacao;
import com.mini.autorizador.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.mini.autorizador.exceptions.CartaoInexistenteException;
import com.mini.autorizador.mini.autorizador.repository.TransacaoRepository;
import com.mini.autorizador.mini.autorizador.service.CartaoService;
import com.mini.autorizador.mini.autorizador.service.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity save(TransacaoDTO transacaoDTO) {

        Transacao transacao = mapper.map(transacaoDTO, Transacao.class);

        ResponseEntity responseEntity = cartaoService.get(transacaoDTO.getNumeroCartao());

        Cartao cartao = mapper.map(responseEntity.getBody(), Cartao.class);

        if (Objects.nonNull(cartao)) {
            cartao.setSaldo(cartao.getSaldo() - transacaoDTO.getValor());
        } else {
            throw new CartaoInexistenteException();
        }


        return null;
    }
}
