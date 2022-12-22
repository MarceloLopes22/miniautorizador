package com.mini.autorizador.mini.autorizador.exceptions;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Saldo Insuficiente");
    }
}
