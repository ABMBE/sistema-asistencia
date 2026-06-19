package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Empleado;
import com.asistencia.sistema_asistencia.repository.EmpleadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> listarEmpleados(){

        return empleadoRepository.findAll();
    }

    public void guardarEmpleado(Empleado empleado){

        empleadoRepository.save(empleado);
    }

    public Empleado obtenerEmpleadoPorId(Integer id){

        return empleadoRepository.findById(id).orElse(null);
    }

    public void eliminarEmpleado(Integer id){

        empleadoRepository.deleteById(id);
    }
    public Empleado buscarPorId(Integer id) {
    return empleadoRepository.findById(id).orElse(null);
}
}