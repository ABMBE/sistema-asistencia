package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.service.JustificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/justificaciones")
public class JustificacionController {

    @Autowired
    private JustificacionService justificacionService;

    /** Vista principal: lista todas las justificaciones */
    @GetMapping
    public String listar(@RequestParam(required = false) String estado, Model model) {
        if (estado != null && !estado.isBlank()) {
            model.addAttribute("justificaciones", justificacionService.listarPorEstado(estado));
            model.addAttribute("estadoFiltro", estado);
        } else {
            model.addAttribute("justificaciones", justificacionService.listarTodas());
            model.addAttribute("estadoFiltro", "");
        }
        return "admin/justificaciones";
    }

    /** Aprobar una justificación */
    @PostMapping("/{id}/aprobar")
    public String aprobar(@PathVariable Integer id) {
        justificacionService.aprobar(id);
        return "redirect:/admin/justificaciones";
    }

    /** Rechazar una justificación */
    @PostMapping("/{id}/rechazar")
    public String rechazar(@PathVariable Integer id) {
        justificacionService.rechazar(id);
        return "redirect:/admin/justificaciones";
    }
}
