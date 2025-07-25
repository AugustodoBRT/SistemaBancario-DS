package com.ProjetoDSbancario.Projeto_DS.exceptions;

public class InvalidTransactionException extends RuntimeException {

    private String subMessage;

    public InvalidTransactionException() {
        super("Transação inválida");
    }

    public InvalidTransactionException(String message) {
        super(message);
    }

    public InvalidTransactionException(String message, String subMessage) {
        super(message);
        this.subMessage = subMessage;
    }

    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }
}