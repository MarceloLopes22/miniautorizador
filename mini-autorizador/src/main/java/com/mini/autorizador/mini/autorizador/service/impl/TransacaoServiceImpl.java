package com.mini.autorizador.mini.autorizador.service.impl;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.domain.Transacao;
import com.mini.autorizador.mini.autorizador.dto.TransacaoDTO;
import com.mini.autorizador.mini.autorizador.exceptions.CartaoInexistenteException;
import com.mini.autorizador.mini.autorizador.exceptions.SenhaInvalidaException;
import com.mini.autorizador.mini.autorizador.service.CartaoService;
import com.mini.autorizador.mini.autorizador.service.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private List<Transacao> transacaos = new ArrayList<>();
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private CartaoService cartaoService;

    @Override
    public ResponseEntity save(TransacaoDTO transacaoDTO) {

        Transacao transacao = mapper.map(transacaoDTO, Transacao.class);

        Cartao cartao = cartaoService.getCartao(transacaoDTO.getNumeroCartao());

        if (Objects.nonNull(cartao)) {

            if (!transacao.getSenhaCartao().equals(cartao.getSenha())) {
                return new ResponseEntity(new SenhaInvalidaException(),
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }

            cartao.setSaldo(cartao.getSaldo() - transacaoDTO.getValor());

            cartaoService.validarSaldo(cartao);

            transacao.setCartao(cartao);

            transacaos.add(transacao);

        } else {
            return new ResponseEntity(new CartaoInexistenteException(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity(transacao, HttpStatus.OK);
    }
}
