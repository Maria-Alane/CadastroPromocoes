package org.example.service;

import org.example.exception.DadosInvalidosException;
import org.example.exception.UsuarioNaoEncontradoException;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.repository.UsuarioRepositoryImpl;
import org.example.util.Mensagens;

import java.util.List;
import java.util.regex.Pattern;


public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final Pattern emailPattern =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepositoryImpl();
    }

    public void criarUsuario(Usuario usuario) {

        validarUsuario(usuario);

        usuarioRepository.salvar(usuario);
    }

    public List<Usuario> listarUsuarios() {

        return usuarioRepository.listarTodos();
    }

    public Usuario buscarPorId(long id) {

        Usuario usuario = usuarioRepository.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(Mensagens.CLIENTE_NAO_ENCONTRADO);
        }

        return usuario;
    }

    public void atualizarUsuario(Usuario usuario) {

        validarUsuario(usuario);

        buscarPorId(usuario.getId());

        usuarioRepository.atualizar(usuario);
    }

    public boolean removerUsuario(long id) {

        buscarPorId(id);

        return usuarioRepository.remover(id);
    }

    private void validarUsuario(Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new DadosInvalidosException(Mensagens.NOME_INVALIDO);
        }

        if (usuario.getEmail() == null ||
                !emailPattern.matcher(usuario.getEmail()).matches()) {

            throw new DadosInvalidosException(Mensagens.EMAIL_INVALIDO);
        }
    }
}
