package com.mini.autorizador.mini.autorizador.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cartao {

    private String numeroCartao;

    private String senha;

    private Double saldo = 500.00;
}
