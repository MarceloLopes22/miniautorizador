package com.mini.autorizador.mini.autorizador.exceptions;

public class CartaoInexistenteException extends RuntimeException {

    public CartaoInexistenteException() {
        super("Cartão inexistente");
    }

}
