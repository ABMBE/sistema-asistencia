package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Area;
import com.asistencia.sistema_asistencia.service.AreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    // LISTAR
    @GetMapping
    public String listarAreas(Model model) {

        model.addAttribute("areas", areaService.listarAreas());

        return "admin/areas";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute("area", new Area());

        return "admin/area-form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarArea(@ModelAttribute Area area) {

        areaService.guardarArea(area);

        return "redirect:/admin/areas";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarArea(@PathVariable Integer id, Model model) {

        Area area = areaService.obtenerAreaPorId(id);

        model.addAttribute("area", area);

        return "admin/area-form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarArea(@PathVariable Integer id) {

        areaService.eliminarArea(id);

        return "redirect:/admin/areas";
    }
}