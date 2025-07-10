package com.compromissos.gerenciadorcompromissos.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompromissoNaoEncontradoException.class)
    public ResponseEntity<String> handleCompromissoNaoEncontradoException(CompromissoNaoEncontradoException compromissoNaoEncontradoException) {
        // Retorna um erro 404 com a mensagem da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(compromissoNaoEncontradoException.getMessage());
    }
}