package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Licencia;
import com.asistencia.sistema_asistencia.repository.LicenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    public List<Licencia> listarLicencias() {
        return licenciaRepository.findAll();
    }

    public void guardarLicencia(Licencia licencia) {
        licenciaRepository.save(licencia);
    }

    public Licencia obtenerLicenciaPorId(Integer id) {
        return licenciaRepository.findById(id).orElse(null);
    }

    public void eliminarLicencia(Integer id) {
        licenciaRepository.deleteById(id);
    }
}