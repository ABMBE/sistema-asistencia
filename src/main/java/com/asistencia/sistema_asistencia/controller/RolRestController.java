package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Rol;
import com.asistencia.sistema_asistencia.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolRestController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> listar() {
        return rolService.listarRoles();
    }

    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable Integer id) {
        return rolService.obtenerRolPorId(id);
    }

    @PostMapping
    public void crear(@RequestBody Rol rol) {
        rolService.guardarRol(rol);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable Integer id, @RequestBody Rol rol) {
        rol.setIdRol(id);
        rolService.guardarRol(rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        rolService.eliminarRol(id);
    }
}