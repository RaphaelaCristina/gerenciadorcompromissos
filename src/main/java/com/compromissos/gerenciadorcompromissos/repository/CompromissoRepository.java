package com.compromissos.gerenciadorcompromissos.repository;

import com.compromissos.gerenciadorcompromissos.entity.CompromissoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompromissoRepository extends JpaRepository<CompromissoEntity, UUID> {

    List<CompromissoEntity> findByIdTelegram(Long idTelegram);

    List<CompromissoEntity> findByDataHoraAndIdTelegram(LocalDateTime dataHora, Long idTelegram);

    Optional<CompromissoEntity> findByCompromissoIdAndIdTelegram(UUID compromissoId, Long idTelegram);

}
