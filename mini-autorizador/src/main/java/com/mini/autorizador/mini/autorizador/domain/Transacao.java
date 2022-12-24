package com.mini.autorizador.mini.autorizador.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transacao {

    private String senhaCartao;

    private Double valor;

    private Cartao cartao;
}
