package com.mini.autorizador.mini.autorizador.service.impl;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.exceptions.CartaoInexistenteException;
import com.mini.autorizador.mini.autorizador.repository.CartaoRepository;
import com.mini.autorizador.mini.autorizador.service.CartaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartaoServiceImpl implements CartaoService {

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity save(CartaoDTO cartaoDTO) {

        Cartao cartao = mapper.map(cartaoDTO, Cartao.class);

        Optional<Cartao> optionalCartao = repository
                .findCartaoByNumeroCartao(cartao.getNumeroCartao());

        if (optionalCartao.isPresent()) {
            return new ResponseEntity(new CartaoInexistenteException(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        cartao = repository.save(cartao);

        return new ResponseEntity(cartao, HttpStatus.OK);
    }

    @Override
    public ResponseEntity get(String numCartao) {
        Cartao cartao = repository.findCartaoByNumeroCartao(numCartao)
                .stream().findAny()
                .orElseThrow();
        return ResponseEntity.ok(cartao);
    }
}
