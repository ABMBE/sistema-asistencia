package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Empleado;
import com.asistencia.sistema_asistencia.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/empleados")

public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private TurnoService turnoService;

    // LISTAR
    @GetMapping
    public String listarEmpleados(Model model){

        model.addAttribute("empleados",
                empleadoService.listarEmpleados());

        return "admin/empleados";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevoEmpleado(Model model){

        model.addAttribute("empleado", new Empleado());

        model.addAttribute("areas",
                areaService.listarAreas());

        model.addAttribute("cargos",
                cargoService.listarCargos());

        model.addAttribute("turnos",
                turnoService.listarTurnos());

        return "admin/empleado-form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleado empleado){

        empleadoService.guardarEmpleado(empleado);

        return "redirect:/admin/empleados";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarEmpleado(@PathVariable Integer id,
                                 Model model){

        model.addAttribute("empleado",
                empleadoService.obtenerEmpleadoPorId(id));

        model.addAttribute("areas",
                areaService.listarAreas());

        model.addAttribute("cargos",
                cargoService.listarCargos());

        model.addAttribute("turnos",
                turnoService.listarTurnos());

        return "admin/empleado-form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Integer id){

        empleadoService.eliminarEmpleado(id);

        return "redirect:/admin/empleados";
    }
}