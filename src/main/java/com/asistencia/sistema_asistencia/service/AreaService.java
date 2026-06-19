package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Area;
import com.asistencia.sistema_asistencia.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    // LISTAR
    public List<Area> listarAreas() {
        return areaRepository.findAll();
    }

    // GUARDAR
    public Area guardarArea(Area area) {
        return areaRepository.save(area);
    }

    // BUSCAR POR ID
    public Area obtenerAreaPorId(Integer id) {
        return areaRepository.findById(id).orElse(null);
    }

    // ELIMINAR
    public void eliminarArea(Integer id) {
        areaRepository.deleteById(id);
    }
}