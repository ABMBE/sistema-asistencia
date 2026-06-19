package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Asistencia;
import com.asistencia.sistema_asistencia.repository.AreaRepository;
import com.asistencia.sistema_asistencia.repository.AsistenciaRepository;
import com.asistencia.sistema_asistencia.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminController {

    @Autowired private AsistenciaRepository asistenciaRepository;
    @Autowired private AreaRepository areaRepository;
    @Autowired private TurnoRepository turnoRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/registros")
    public String verRegistros(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam(required = false) String area,
        @RequestParam(required = false) String turno,
        @RequestParam(required = false) String estado,
        Model model
    ) {
        List<Asistencia> registros = asistenciaRepository.filtrar(fecha, area, turno, estado);

        model.addAttribute("registros", registros);
        model.addAttribute("areas", areaRepository.findAll());
        model.addAttribute("turnos", turnoRepository.findAll());
        model.addAttribute("filtroFecha", fecha != null ? fecha.toString() : "");
        model.addAttribute("filtroArea", area != null ? area : "");
        model.addAttribute("filtroTurno", turno != null ? turno : "");
        model.addAttribute("filtroEstado", estado != null ? estado : "");

        return "admin/ver-registros";
    }

    // Nota: /admin/justificaciones ya está manejado por JustificacionController
}