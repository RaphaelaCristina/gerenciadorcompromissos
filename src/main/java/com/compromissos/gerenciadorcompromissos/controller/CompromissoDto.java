package com.compromissos.gerenciadorcompromissos.controller;

import java.time.LocalDateTime;

public record CompromissoDto(
        LocalDateTime dataHora,
        String descricao,
        String local,
        Long idTelegram
) {

}
