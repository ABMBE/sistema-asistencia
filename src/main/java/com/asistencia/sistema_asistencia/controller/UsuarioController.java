package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Empleado;
import com.asistencia.sistema_asistencia.model.Rol;
import com.asistencia.sistema_asistencia.model.Usuario;
import com.asistencia.sistema_asistencia.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private RolService rolService;

    // LISTAR

    @GetMapping
    public String listarUsuarios(Model model){

        model.addAttribute("usuarios",
                usuarioService.listarUsuarios());

        return "admin/usuarios";
    }

    // NUEVO

@GetMapping("/nuevo")
public String nuevoUsuario(Model model){

    Usuario usuario = new Usuario();

    usuario.setRol(new Rol());

    usuario.setEmpleado(new Empleado());

    model.addAttribute("usuario", usuario);

    model.addAttribute("empleados",
            empleadoService.listarEmpleados());

    model.addAttribute("roles",
            rolService.listarRoles());

    return "admin/usuario-form";
}

    // GUARDAR

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario){

        usuarioService.guardarUsuario(usuario);

        return "redirect:/admin/usuarios";
    }

    // EDITAR

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id,
                                Model model){

        model.addAttribute("usuario",
                usuarioService.obtenerUsuarioPorId(id));

        model.addAttribute("empleados",
                empleadoService.listarEmpleados());

        model.addAttribute("roles",
                rolService.listarRoles());

        return "admin/usuario-form";
    }

    // ELIMINAR

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id){

        usuarioService.eliminarUsuario(id);

        return "redirect:/admin/usuarios";
    }
}