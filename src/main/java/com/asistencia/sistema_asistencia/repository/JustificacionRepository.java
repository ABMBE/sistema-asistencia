package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Justificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JustificacionRepository extends JpaRepository<Justificacion, Integer> {

    List<Justificacion> findByEmpleado_IdEmpleado(Integer idEmpleado);

    List<Justificacion> findByEstado(String estado);

    List<Justificacion> findByEmpleado_IdEmpleadoAndEstado(Integer idEmpleado, String estado);
}