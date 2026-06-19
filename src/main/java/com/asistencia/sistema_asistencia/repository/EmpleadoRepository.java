package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository
        extends JpaRepository<Empleado, Integer> {

}