package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Licencia;
import com.asistencia.sistema_asistencia.service.EmpleadoService;
import com.asistencia.sistema_asistencia.service.LicenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/licencias")
public class LicenciaController {

    @Autowired
    private LicenciaService licenciaService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String listarLicencias(Model model) {
        model.addAttribute("licencias", licenciaService.listarLicencias());
        model.addAttribute("licencia", new Licencia());
        model.addAttribute("empleados", empleadoService.listarEmpleados());

        return "admin/licencias";
    }

    @PostMapping("/guardar")
    public String guardarLicencia(@ModelAttribute Licencia licencia) {
        licenciaService.guardarLicencia(licencia);
        return "redirect:/admin/licencias";
    }

    @GetMapping("/aprobar/{id}")
    public String aprobarLicencia(@PathVariable Integer id) {
        Licencia licencia = licenciaService.obtenerLicenciaPorId(id);
        licencia.setEstado("APROBADO");
        licenciaService.guardarLicencia(licencia);

        return "redirect:/admin/licencias";
    }

    @GetMapping("/rechazar/{id}")
    public String rechazarLicencia(@PathVariable Integer id) {
        Licencia licencia = licenciaService.obtenerLicenciaPorId(id);
        licencia.setEstado("RECHAZADO");
        licenciaService.guardarLicencia(licencia);

        return "redirect:/admin/licencias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLicencia(@PathVariable Integer id) {
        licenciaService.eliminarLicencia(id);
        return "redirect:/admin/licencias";
    }
}