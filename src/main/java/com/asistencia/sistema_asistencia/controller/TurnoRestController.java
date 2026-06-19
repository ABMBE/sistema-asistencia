package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Turno;
import com.asistencia.sistema_asistencia.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoRestController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public List<Turno> listar() {
        return turnoService.listarTurnos();
    }

    @GetMapping("/{id}")
    public Turno obtenerPorId(@PathVariable Integer id) {
        return turnoService.obtenerTurnoPorId(id);
    }

    @PostMapping
    public void crear(@RequestBody Turno turno) {
        turnoService.guardarTurno(turno);
    }

    @PutMapping("/{id}")
    public void actualizar(@PathVariable Integer id, @RequestBody Turno turno) {
        turno.setIdTurno(id);
        turnoService.guardarTurno(turno);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);
    }
}