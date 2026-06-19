package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Empleado;
import com.asistencia.sistema_asistencia.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoRestController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable Integer id) {
        return empleadoService.obtenerEmpleadoPorId(id);
    }

    @PostMapping
    public void crear(@RequestBody Empleado empleado) {
        empleadoService.guardarEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable Integer id, @RequestBody Empleado empleado) {
        empleado.setIdEmpleado(id);
        empleadoService.guardarEmpleado(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        empleadoService.eliminarEmpleado(id);
    }
}