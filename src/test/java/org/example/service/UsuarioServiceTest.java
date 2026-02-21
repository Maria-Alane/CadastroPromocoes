package org.example.service;

import org.example.exception.DadosInvalidosException;
import org.example.exception.UsuarioNaoEncontradoException;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = new Usuario("Maria", "maria@email.com");
    }

    @Test
    void deveCriarUsuarioComDadosValidos() {
        service.criarUsuario(usuario);

        verify(repository, times(1)).save(usuario);
    }

    @Test
    void naoDeveCriarUsuarioComNomeInvalido() {
        Usuario invalido = new Usuario("", "email@email.com");

        assertThrows(DadosInvalidosException.class,
                () -> service.criarUsuario(invalido));

        verify(repository, never()).save(any());
    }

    @Test
    void naoDeveCriarUsuarioComEmailInvalido() {
        Usuario invalido = new Usuario("Maria", "emailinvalido");

        assertThrows(DadosInvalidosException.class,
                () -> service.criarUsuario(invalido));

        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> service.buscarPorId(1L));
    }

    @Test
    void deveRemoverUsuarioQuandoExistir() {
        when(repository.existsById(1L)).thenReturn(true);

        service.removerUsuario(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoRemoverUsuarioInexistente() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> service.removerUsuario(1L));
    }
}