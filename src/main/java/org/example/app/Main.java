package org.example.app;

import org.example.exception.DadosInvalidosException;
import org.example.exception.UsuarioNaoEncontradoException;
import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.example.util.Mensagens;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final UsuarioService usuarioService =
            new UsuarioService();

    static void main(String[] args) {

        boolean executando = true;

        while (executando) {

            exibirMenu();

            int opcao = lerOpcao();

            try {

                switch (opcao) {

                    case 1 -> criarUsuario();
                    case 2 -> listarUsuarios();
                    case 3 -> buscarUsuario();
                    case 4 -> atualizarUsuario();
                    case 5 -> removerUsuario();
                    case 0 -> executando = false;

                    default -> System.out.println(Mensagens.OPCAO_INVALIDA);
                }

            } catch (DadosInvalidosException |
                     UsuarioNaoEncontradoException e) {

                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.println(Mensagens.SISTEMA_FINALIZADO);
    }

    private static void exibirMenu() {

        System.out.println(Mensagens.MENU_TITULO);
        System.out.println(Mensagens.MENU_OPCAO_CADASTRAR);
        System.out.println(Mensagens.MENU_OPCAO_LISTAR);
        System.out.println(Mensagens.MENU_OPCAO_BUSCAR);
        System.out.println(Mensagens.MENU_OPCAO_ATUALIZAR);
        System.out.println(Mensagens.MENU_OPCAO_REMOVER);
        System.out.println(Mensagens.MENU_OPCAO_SAIR);
    }

    private static void criarUsuario() {

        System.out.print(Mensagens.INPUT_NOME);
        String nome = scanner.nextLine();

        System.out.print(Mensagens.INPUT_EMAIL);
        String email = scanner.nextLine();

        Usuario usuario = new Usuario(0, nome, email);

        usuarioService.criarUsuario(usuario);

        System.out.println(Mensagens.CLIENTE_CADASTRADO_SUCESSO);
    }

    private static void listarUsuarios() {

        var usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()) {

            System.out.println(Mensagens.SEM_CLIENTES);

            return;
        }

        System.out.println(Mensagens.CLIENTES_CADASTRADOS);

        usuarios.forEach(System.out::println);
    }


    private static void buscarUsuario() {

        System.out.print(Mensagens.ID);
        long id = Long.parseLong(scanner.nextLine());

        Usuario usuario =
                usuarioService.buscarPorId(id);

        System.out.println(usuario);
    }

    private static void atualizarUsuario() {

        System.out.print(Mensagens.ID);
        long id = Long.parseLong(scanner.nextLine());

        try {

            usuarioService.buscarPorId(id);

        } catch (UsuarioNaoEncontradoException e) {

            System.out.println(Mensagens.CLIENTE_NAO_ENCONTRADO);

            return;
        }

        System.out.print(Mensagens.NOVO_NOME);
        String nome = scanner.nextLine();

        System.out.print(Mensagens.NOVO_EMAIL);
        String email = scanner.nextLine();

        Usuario usuario =
                new Usuario(id, nome, email);

        usuarioService.atualizarUsuario(usuario);

        System.out.println(Mensagens.CLIENTE_ATUALIZADO);
    }



    private static void removerUsuario() {

        System.out.print(Mensagens.ID);
        long id = Long.parseLong(scanner.nextLine());

        usuarioService.removerUsuario(id);

        System.out.println(Mensagens.CLIENTE_REMOVIDO);
    }

    private static int lerOpcao() {

        while (true) {

            System.out.print(Mensagens.DIGITE_UMA_OPCAO_VALIDA);

            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {

                System.out.println(Mensagens.OPCAO_NAO_PODE_SER_VAZIA);

                continue;
            }

            try {


                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {

                System.out.println(Mensagens.DIGITE_APENAS_NUMEROS);
            }
        }
    }

}
