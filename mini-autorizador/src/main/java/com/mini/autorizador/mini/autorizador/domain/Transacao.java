package com.mini.autorizador.mini.autorizador.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String senhaCartao;

    private Double valor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_id", referencedColumnName = "id")
    private Cartao cartao;
}
