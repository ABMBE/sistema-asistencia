package com.asistencia.sistema_asistencia.repository;

import com.asistencia.sistema_asistencia.model.Asistencia;
import com.asistencia.sistema_asistencia.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    Asistencia findByEmpleadoAndFecha(Empleado empleado, LocalDate fecha);

    @Query("SELECT a FROM Asistencia a WHERE " +
           "(:fecha IS NULL OR a.fecha = :fecha) AND " +
           "(:area IS NULL OR a.empleado.area.nombreArea = :area) AND " +
           "(:turno IS NULL OR a.empleado.turno.nombreTurno = :turno) AND " +
           "(:estado IS NULL OR a.estado = :estado)")
    List<Asistencia> filtrar(
        @Param("fecha")  LocalDate fecha,
        @Param("area")   String area,
        @Param("turno")  String turno,
        @Param("estado") String estado
    );

    @Query("SELECT a FROM Asistencia a WHERE " +
           "(:desde IS NULL OR a.fecha >= :desde) AND " +
           "(:hasta IS NULL OR a.fecha <= :hasta) AND " +
           "(:area IS NULL OR a.empleado.area.nombreArea = :area) AND " +
           "(:turno IS NULL OR a.empleado.turno.nombreTurno = :turno) AND " +
           "(:idEmpleado IS NULL OR a.empleado.idEmpleado = :idEmpleado)")
    List<Asistencia> filtrarReporte(
        @Param("desde")      LocalDate desde,
        @Param("hasta")      LocalDate hasta,
        @Param("area")       String area,
        @Param("turno")      String turno,
        @Param("idEmpleado") Integer idEmpleado
    );
}
