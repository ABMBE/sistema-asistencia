package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Cargo;
import com.asistencia.sistema_asistencia.repository.CargoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> listarCargos(){
        return cargoRepository.findAll();
    }

    public void guardarCargo(Cargo cargo){
        cargoRepository.save(cargo);
    }

    public Cargo obtenerCargoPorId(Integer id){
        return cargoRepository.findById(id).orElse(null);
    }

    public void eliminarCargo(Integer id){
        cargoRepository.deleteById(id);
    }
}