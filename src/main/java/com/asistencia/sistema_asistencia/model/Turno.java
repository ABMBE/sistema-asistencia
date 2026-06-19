package com.asistencia.sistema_asistencia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter

@Entity
@Table(name = "turno")

public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Integer idTurno;

    @Column(name = "nombre_turno", nullable = false)
    private String nombreTurno;

    @Column(name = "hora_inicio")
    private LocalTime horaEntrada;

    @Column(name = "hora_fin")
    private LocalTime horaSalida;

    @Column(name = "estado")
    private Boolean estado;
}