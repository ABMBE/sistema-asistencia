package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Cargo;
import com.asistencia.sistema_asistencia.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
public class CargoRestController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<Cargo> listar() {
        return cargoService.listarCargos();
    }

    @GetMapping("/{id}")
    public Cargo obtenerPorId(@PathVariable Integer id) {
        return cargoService.obtenerCargoPorId(id);
    }

    @PostMapping
    public void crear(@RequestBody Cargo cargo) {
        cargoService.guardarCargo(cargo);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable Integer id, @RequestBody Cargo cargo) {
        cargo.setIdCargo(id);
        cargoService.guardarCargo(cargo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        cargoService.eliminarCargo(id);
    }
}