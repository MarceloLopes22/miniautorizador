package com.mini.autorizador.mini.autorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartaoDTO {

    private String numeroCartao;

    private String senha;
}
