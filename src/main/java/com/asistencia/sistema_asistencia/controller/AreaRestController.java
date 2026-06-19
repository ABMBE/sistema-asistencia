package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Area;
import com.asistencia.sistema_asistencia.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaRestController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public List<Area> listar() {
        return areaService.listarAreas();
    }

    @GetMapping("/{id}")
    public Area obtenerPorId(@PathVariable Integer id) {
        return areaService.obtenerAreaPorId(id);
    }

    @PostMapping
    public Area crear(@RequestBody Area area) {
        return areaService.guardarArea(area);
    }

    @PutMapping("/{id}")
    public Area actualizar(@PathVariable Integer id, @RequestBody Area area) {
        area.setIdArea(id);
        return areaService.guardarArea(area);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        areaService.eliminarArea(id);
    }
}