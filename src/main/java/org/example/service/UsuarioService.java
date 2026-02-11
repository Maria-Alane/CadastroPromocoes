package org.example.service;

import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {


    private final UsuarioRepository usuarioRepository;


    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void criarUsuario(Usuario usuario) {
        usuarioRepository.salvar(usuario);
    }


    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }


    public Usuario buscarPorId(long id) {
        return usuarioRepository.buscarPorId(id);
    }


    public void atualizarUsuario(Usuario usuario) {
        usuarioRepository.atualizar(usuario);
    }


    public boolean removerUsuario(long id) {
        return usuarioRepository.remover(id);
    }

}
