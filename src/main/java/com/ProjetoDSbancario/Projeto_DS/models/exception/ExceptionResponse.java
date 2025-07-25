package com.ProjetoDSbancario.Projeto_DS.models.exception;

public class ExceptionResponse {
    private String message;
    private String subMessage;

    public ExceptionResponse(String message, String subMessage) {
        this.message = message;
        this.subMessage = subMessage;
    }

    public String getMessage() {
        return message;
    }

    public String getSubMessage() {
        return subMessage;
    }
}