package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Turno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository
        extends JpaRepository<Turno, Integer> {

}