package org.example.repository;

import org.example.model.Usuario;

import java.util.List;

public interface UsuarioRepository {

    void salvar(Usuario usuario);

    List<Usuario> listarTodos();

    Usuario buscarPorId(long id);

    void atualizar(Usuario usuario);

    boolean remover(long id);
}
