package com.mini.autorizador.mini.autorizador.exceptions;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha invalida");
    }
}
