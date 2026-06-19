package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Empleado;
import com.asistencia.sistema_asistencia.model.Justificacion;
import com.asistencia.sistema_asistencia.service.EmpleadoService;
import com.asistencia.sistema_asistencia.service.JustificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/empleado")
public class EmpleadoVistaController {

    @Autowired
    private JustificacionService justificacionService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "empleado/dashboard";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "empleado/perfil";
    }

    @GetMapping("/asistencia")
    public String asistencia() {
        return "empleado/asistencia";
    }

    @GetMapping("/justificaciones")
    public String justificaciones(Model model) {
        Integer idEmpleado = 1;
        model.addAttribute("justificaciones", justificacionService.listarPorEmpleado(idEmpleado));
        model.addAttribute("nuevaJustificacion", new Justificacion());
        return "empleado/justificaciones";
    }

    @PostMapping("/justificaciones")
    public String enviarJustificacion(@ModelAttribute Justificacion justificacion,
                                      @RequestParam("archivo") MultipartFile archivo) throws IOException {
        if (!archivo.isEmpty()) {
            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path ruta = Paths.get("uploads/" + nombreArchivo);
            Files.createDirectories(ruta.getParent());
            Files.write(ruta, archivo.getBytes());
            justificacion.setArchivoAdjunto(nombreArchivo);
        }
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(1);
        justificacion.setEmpleado(empleado);
        justificacionService.registrar(justificacion);
        return "redirect:/empleado/justificaciones";
    }
}