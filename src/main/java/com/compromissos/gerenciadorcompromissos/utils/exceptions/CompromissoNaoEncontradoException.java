package com.compromissos.gerenciadorcompromissos.utils.exceptions;

import java.util.UUID;

public class CompromissoNaoEncontradoException extends RuntimeException {

    public CompromissoNaoEncontradoException(UUID compromissoId) {
        super("Compromisso de ID: " + compromissoId + " não localizado.");
    }
}