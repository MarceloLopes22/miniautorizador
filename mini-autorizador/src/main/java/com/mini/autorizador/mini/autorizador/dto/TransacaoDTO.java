package com.mini.autorizador.mini.autorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoDTO {

    private String numeroCartao;

    private String senhaCartao;

    private Double valor;
}
