package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}