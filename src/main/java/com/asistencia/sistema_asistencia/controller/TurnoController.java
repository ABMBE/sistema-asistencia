package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Turno;
import com.asistencia.sistema_asistencia.service.TurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/turnos")

public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    // LISTAR
    @GetMapping
    public String listarTurnos(Model model){

        model.addAttribute("turnos",
                turnoService.listarTurnos());

        return "admin/turnos";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevoTurno(Model model){

        model.addAttribute("turno", new Turno());

        return "admin/turno-form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarTurno(@ModelAttribute Turno turno){

        turnoService.guardarTurno(turno);

        return "redirect:/admin/turnos";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarTurno(@PathVariable Integer id,
                              Model model){

        model.addAttribute("turno",
                turnoService.obtenerTurnoPorId(id));

        return "admin/turno-form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarTurno(@PathVariable Integer id){

        turnoService.eliminarTurno(id);

        return "redirect:/admin/turnos";
    }
}