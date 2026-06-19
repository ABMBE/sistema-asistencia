package com.asistencia.sistema_asistencia.service;

import com.asistencia.sistema_asistencia.model.Usuario;
import com.asistencia.sistema_asistencia.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios(){

        return usuarioRepository.findAll();
    }

    public void guardarUsuario(Usuario usuario){

        usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(Integer id){

        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminarUsuario(Integer id){

        usuarioRepository.deleteById(id);
    }
}