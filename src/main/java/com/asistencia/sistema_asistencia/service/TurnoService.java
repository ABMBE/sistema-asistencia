package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Turno;
import com.asistencia.sistema_asistencia.repository.TurnoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    public List<Turno> listarTurnos(){

        return turnoRepository.findAll();
    }

    public void guardarTurno(Turno turno){

        turnoRepository.save(turno);
    }

    public Turno obtenerTurnoPorId(Integer id){

        return turnoRepository.findById(id).orElse(null);
    }

    public void eliminarTurno(Integer id){

        turnoRepository.deleteById(id);
    }
}