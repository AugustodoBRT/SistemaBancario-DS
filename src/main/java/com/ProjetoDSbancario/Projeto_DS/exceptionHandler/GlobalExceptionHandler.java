package com.ProjetoDSbancario.Projeto_DS.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ProjetoDSbancario.Projeto_DS.exceptions.InvalidCpfException;
import com.ProjetoDSbancario.Projeto_DS.exceptions.InvalidPixException;
import com.ProjetoDSbancario.Projeto_DS.exceptions.InvalidTransactionException;
import com.ProjetoDSbancario.Projeto_DS.models.exception.ExceptionResponse;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse("","Credenciais incorretas");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse("","Entidade não encontrada");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTransaction(InvalidTransactionException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                ex.getSubMessage() != null ? ex.getSubMessage() : "");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCpf(InvalidCpfException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                ex.getSubMessage() != null ? ex.getSubMessage() : "");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InvalidPixException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCpf(InvalidPixException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                ex.getSubMessage() != null ? ex.getSubMessage() : "");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }
}