package org.example.service;

import org.example.exception.DadosInvalidosException;
import org.example.exception.UsuarioNaoEncontradoException;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.util.Mensagens;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final Pattern emailPattern =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void criarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        repository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException(Mensagens.CLIENTE_NAO_ENCONTRADO));
    }

    public void atualizarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        buscarPorId(usuario.getId());
        repository.save(usuario);
    }

    public void removerUsuario(long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNaoEncontradoException(Mensagens.CLIENTE_NAO_ENCONTRADO);
        }
        repository.deleteById(id);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new DadosInvalidosException(Mensagens.NOME_INVALIDO);
        }
        if (usuario.getEmail() == null || !emailPattern.matcher(usuario.getEmail()).matches()) {
            throw new DadosInvalidosException(Mensagens.EMAIL_INVALIDO);
        }
    }
}
