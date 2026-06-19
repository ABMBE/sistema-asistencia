package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Cargo;
import com.asistencia.sistema_asistencia.service.AreaService;
import com.asistencia.sistema_asistencia.service.CargoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;
    @Autowired
private AreaService areaService;

    // LISTAR
    @GetMapping
    public String listarCargos(Model model){

        model.addAttribute("cargos", cargoService.listarCargos());
        model.addAttribute("areas", areaService.listarAreas());

        return "admin/cargos";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevoCargo(Model model){

        model.addAttribute("cargo", new Cargo());
        model.addAttribute("areas", areaService.listarAreas());

        return "admin/cargo-form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarCargo(@ModelAttribute Cargo cargo){

        cargoService.guardarCargo(cargo);

        return "redirect:/admin/cargos";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarCargo(@PathVariable Integer id, Model model){

        model.addAttribute("cargo",
                cargoService.obtenerCargoPorId(id));
        model.addAttribute("areas", areaService.listarAreas());

        return "admin/cargo-form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarCargo(@PathVariable Integer id){

        cargoService.eliminarCargo(id);

        return "redirect:/admin/cargos";
    }
}