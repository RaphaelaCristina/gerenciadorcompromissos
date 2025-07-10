package com.compromissos.gerenciadorcompromissos.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_compromissos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompromissoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID compromissoId;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "local")
    private String local;

    @Column(name = "id_telegram", nullable = false)
    private Long idTelegram;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updateTimestamp;
}
