package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}