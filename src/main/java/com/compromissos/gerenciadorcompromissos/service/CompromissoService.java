package com.compromissos.gerenciadorcompromissos.service;

import com.compromissos.gerenciadorcompromissos.controller.CompromissoDto;
import com.compromissos.gerenciadorcompromissos.entity.CompromissoEntity;
import com.compromissos.gerenciadorcompromissos.mapper.CompromissoMapper;
import com.compromissos.gerenciadorcompromissos.repository.CompromissoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompromissoService {

    @Autowired
    private CompromissoRepository compromissoRepository;

    @Autowired
    private CompromissoMapper compromissoMapper;

    public CompromissoService(CompromissoRepository compromissoRepository) {
        this.compromissoRepository = compromissoRepository;
    }

    public CompromissoEntity createCompromisso(CompromissoDto createCompromissoDto, Long idTelegram) {
        CompromissoEntity entity = compromissoMapper.toEntity(createCompromissoDto);
        entity.setIdTelegram(idTelegram);
        return compromissoRepository.save(entity);
    }

    public Optional<CompromissoEntity> getCompromissoById(UUID id, Long idTelegram) {
        return compromissoRepository.findByCompromissoIdAndIdTelegram(id, idTelegram);
    }


    public List<CompromissoEntity> getTodosCompromissos(Long idTelegram) {
        return compromissoRepository.findByIdTelegram(idTelegram);
    }

    public List<CompromissoEntity> getCompromissosPorDia(LocalDateTime dataHora, Long idTelegram) {
        return compromissoRepository.findByDataHoraAndIdTelegram(dataHora, idTelegram);
    }
}
