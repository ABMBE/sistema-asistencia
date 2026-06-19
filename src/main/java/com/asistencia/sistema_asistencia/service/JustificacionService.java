package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Justificacion;
import com.asistencia.sistema_asistencia.repository.JustificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JustificacionService {

    @Autowired
    private JustificacionRepository justificacionRepository;

    // ─── Empleado ───────────────────────────────────────────

    /** Lista todas las justificaciones de un empleado */
    public List<Justificacion> listarPorEmpleado(Integer idEmpleado) {
        return justificacionRepository.findByEmpleado_IdEmpleado(idEmpleado);
    }

    /** Registra una nueva justificación con estado PENDIENTE */
    public void registrar(Justificacion justificacion) {
        justificacion.setEstado("PENDIENTE");
        justificacion.setFechaRegistro(LocalDateTime.now());
        justificacionRepository.save(justificacion);
    }

    // ─── Admin ───────────────────────────────────────────────

    /** Lista todas las justificaciones del sistema */
    public List<Justificacion> listarTodas() {
        return justificacionRepository.findAll();
    }

    /** Lista justificaciones filtradas por estado */
    public List<Justificacion> listarPorEstado(String estado) {
        return justificacionRepository.findByEstado(estado);
    }

    /** Aprueba una justificación */
    public void aprobar(Integer id) {
        Justificacion j = justificacionRepository.findById(id).orElseThrow();
        j.setEstado("APROBADO");
        justificacionRepository.save(j);
    }

    /** Rechaza una justificación */
    public void rechazar(Integer id) {
        Justificacion j = justificacionRepository.findById(id).orElseThrow();
        j.setEstado("RECHAZADO");
        justificacionRepository.save(j);
    }

    /** Obtiene una justificación por su ID */
    public Justificacion obtenerPorId(Integer id) {
        return justificacionRepository.findById(id).orElse(null);
    }
}
