package com.compromissos.gerenciadorcompromissos.controller;

import com.compromissos.gerenciadorcompromissos.entity.CompromissoEntity;
import com.compromissos.gerenciadorcompromissos.service.CompromissoService;
import com.compromissos.gerenciadorcompromissos.utils.exceptions.CompromissoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/compromissos")
public class CompromissoController {

    private final CompromissoService compromissoService;

    public CompromissoController(CompromissoService compromissoService) {
        this.compromissoService = compromissoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompromissoEntity createCompromisso(@RequestBody CompromissoDto createCompromissoDto) {
        return compromissoService.createCompromisso(createCompromissoDto, createCompromissoDto.idTelegram());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompromissoEntity> getCompromissoById(
            @PathVariable UUID id,
            @RequestParam("idTelegram") Long idTelegram
    ) {
        return compromissoService.getCompromissoById(id, idTelegram)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CompromissoEntity>> getAllCompromissos(@RequestParam("idTelegram") Long idTelegram) {
        List<CompromissoEntity> compromissos = compromissoService.getTodosCompromissos(idTelegram);
        return ResponseEntity.ok(compromissos);
    }

}
