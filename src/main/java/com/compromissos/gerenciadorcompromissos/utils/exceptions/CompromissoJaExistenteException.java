package com.compromissos.gerenciadorcompromissos.utils.exceptions;

import java.util.UUID;

public class CompromissoJaExistenteException extends RuntimeException {
    public CompromissoJaExistenteException(UUID compromissoId) {
        super("O compromisso com ID " + compromissoId + " já foi adicionado anteriormente.");
    }
}