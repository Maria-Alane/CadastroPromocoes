package org.example.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {


    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}