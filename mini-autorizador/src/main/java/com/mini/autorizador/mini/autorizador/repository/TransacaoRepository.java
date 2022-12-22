package com.mini.autorizador.mini.autorizador.repository;

import com.mini.autorizador.mini.autorizador.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

}
