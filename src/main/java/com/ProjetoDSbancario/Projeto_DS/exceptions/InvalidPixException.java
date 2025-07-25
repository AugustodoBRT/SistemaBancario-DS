package com.ProjetoDSbancario.Projeto_DS.exceptions;

public class InvalidPixException extends RuntimeException {

    private String subMessage;

    public InvalidPixException() {
        super("Chave pix já está sendo utilizada");
    }

    public InvalidPixException(String message) {
        super(message);
    }

    public InvalidPixException(String message, String subMessage) {
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