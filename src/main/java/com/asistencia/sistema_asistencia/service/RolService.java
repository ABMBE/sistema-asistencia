package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Rol;
import com.asistencia.sistema_asistencia.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarRoles(){

        return rolRepository.findAll();
    }

    public void guardarRol(Rol rol){

        rolRepository.save(rol);
    }

    public Rol obtenerRolPorId(Integer id){

        return rolRepository.findById(id).orElse(null);
    }

    public void eliminarRol(Integer id){

        rolRepository.deleteById(id);
    }
}