package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {
}