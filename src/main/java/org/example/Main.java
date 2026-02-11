package org.example;

import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class Main {


    private static final Scanner scanner = new Scanner(System.in);


    private static final UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {

        boolean executando = true;

        while (executando) {


            exibirMenu();


            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {

                case 1 -> criarUsuario();

                case 2 -> listarUsuarios();

                case 3 -> buscarUsuarioPorId();

                case 4 -> atualizarUsuario();

                case 5 -> removerUsuario();

                case 0 -> {
                    executando = false;
                    System.out.println("Encerrando o sistema...");
                }

                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void exibirMenu() {

        System.out.println("\n===== MENU =====");
        System.out.println("1 - Criar usuário");
        System.out.println("2 - Listar usuários");
        System.out.println("3 - Buscar usuário por ID");
        System.out.println("4 - Atualizar usuário");
        System.out.println("5 - Remover usuário");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }


    private static void criarUsuario() {

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Usuario usuario = new Usuario(0, nome, email);

        usuarioService.criarUsuario(usuario);

        System.out.println("Usuário cadastrado com sucesso.");
    }


    private static void listarUsuarios() {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        System.out.println("\n=== Usuários ===");

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }


    private static void buscarUsuarioPorId() {

        System.out.print("Informe o ID: ");
        long id = Long.parseLong(scanner.nextLine());

        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
        } else {
            System.out.println(usuario);
        }
    }


    private static void atualizarUsuario() {

        System.out.print("Informe o ID: ");
        long id = Long.parseLong(scanner.nextLine());

        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        usuario.setNome(nome);
        usuario.setEmail(email);

        usuarioService.atualizarUsuario(usuario);

        System.out.println("Usuário atualizado com sucesso.");
    }


    private static void removerUsuario() {

        System.out.print("Informe o ID: ");
        long id = Long.parseLong(scanner.nextLine());

        boolean removido = usuarioService.removerUsuario(id);

        if (removido) {
            System.out.println("Usuário removido com sucesso.");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }


}
