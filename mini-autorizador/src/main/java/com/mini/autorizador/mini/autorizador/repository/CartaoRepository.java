package com.mini.autorizador.mini.autorizador.repository;

import com.mini.autorizador.mini.autorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    Optional<Cartao> findCartaoByNumeroCartao(String numeroCartao);

}
