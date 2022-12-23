package com.mini.autorizador.mini.autorizador.service.impl;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import com.mini.autorizador.mini.autorizador.dto.CartaoDTO;
import com.mini.autorizador.mini.autorizador.exceptions.CartaoInexistenteException;
import com.mini.autorizador.mini.autorizador.exceptions.SaldoInsuficienteException;
import com.mini.autorizador.mini.autorizador.service.CartaoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartaoServiceImpl implements CartaoService {

    private List<Cartao> cartoes = new ArrayList<>();

    private ModelMapper mapper = new ModelMapper();

    @Override
    public ResponseEntity save(CartaoDTO cartaoDTO) {

        ResponseEntity response = get(cartaoDTO.getNumeroCartao());

        Cartao cartao = mapper.map(response.getBody(), Cartao.class);

        if (Objects.isNull(cartao)) {
            return new ResponseEntity(new CartaoInexistenteException(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        cartoes.add(cartao);

        validarSaldo(cartao);

        return new ResponseEntity(cartao, HttpStatus.OK);
    }
    @Override
    public ResponseEntity validarSaldo(Cartao cartao) {
        if (cartao.getSaldo() <= 0) {
            return new ResponseEntity(new SaldoInsuficienteException(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
          return null;
    }

    @Override
    public ResponseEntity get(String numCartao) {

        Cartao cartao = cartoes.stream()
                .filter(obj -> obj.getNumeroCartao().equals(numCartao))
                .findAny()
                .orElseThrow();

        return ResponseEntity.ok(cartao);
    }
}
