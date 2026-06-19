package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Rol;
import com.asistencia.sistema_asistencia.service.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")

public class RolController {

    @Autowired
    private RolService rolService;

    // LISTAR

    @GetMapping
    public String listarRoles(Model model){

        model.addAttribute("roles",
                rolService.listarRoles());

        return "admin/roles";
    }

    // NUEVO

    @GetMapping("/nuevo")
    public String nuevoRol(Model model){

        model.addAttribute("rol", new Rol());

        return "admin/rol-form";
    }

    // GUARDAR

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute Rol rol){

        rolService.guardarRol(rol);

        return "redirect:/admin/roles";
    }

    // EDITAR

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable Integer id,
                            Model model){

        model.addAttribute("rol",
                rolService.obtenerRolPorId(id));

        return "admin/rol-form";
    }

    // ELIMINAR

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Integer id){

        rolService.eliminarRol(id);

        return "redirect:/admin/roles";
    }
}