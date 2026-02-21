package org.example;

import org.example.exception.DadosInvalidosException;
import org.example.exception.UsuarioNaoEncontradoException;
import org.example.exception.VoltarMenuException;
import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.example.util.Mensagens;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class CadastroPromocoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadastroPromocoesApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UsuarioService usuarioService) {
        return args -> {

            Scanner scanner = new Scanner(System.in);
            boolean executando = true;

            while (executando) {
                exibirMenu();

                try {
                    int opcao = lerOpcao(scanner);

                    switch (opcao) {
                        case 1 -> criarUsuario(scanner, usuarioService);
                        case 2 -> listarUsuarios(usuarioService);
                        case 3 -> buscarUsuario(scanner, usuarioService);
                        case 4 -> atualizarUsuario(scanner, usuarioService);
                        case 5 -> removerUsuario(scanner, usuarioService);
                        case 0 -> executando = false;
                        default -> System.out.println(Mensagens.OPCAO_INVALIDA);
                    }

                } catch (VoltarMenuException e) {
                    System.out.println(Mensagens.VOLTAR_MENU);
                } catch (DadosInvalidosException | UsuarioNaoEncontradoException e) {
                    System.out.println("Erro: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erro inesperado: " + e.getMessage());
                }

            }

            System.out.println(Mensagens.SISTEMA_FINALIZADO);
        };
    }

    private void exibirMenu() {
        System.out.println(Mensagens.MENU_TITULO);
        System.out.println(Mensagens.MENU_OPCAO_CADASTRAR);
        System.out.println(Mensagens.MENU_OPCAO_LISTAR);
        System.out.println(Mensagens.MENU_OPCAO_BUSCAR);
        System.out.println(Mensagens.MENU_OPCAO_ATUALIZAR);
        System.out.println(Mensagens.MENU_OPCAO_REMOVER);
        System.out.println(Mensagens.MENU_OPCAO_SAIR);
    }

    private int lerOpcao(Scanner scanner) {
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

    private String lerEntradaOuSair(Scanner scanner) {
        String entrada = scanner.nextLine().trim();

        if (entrada.equals("0")) {
            throw new VoltarMenuException();
        }
        return entrada;
    }

    private void criarUsuario(Scanner scanner, UsuarioService service) {

        String nome;

        while (true) {
            System.out.print(Mensagens.INPUT_NOME);
            nome = lerEntradaOuSair(scanner);

            if (nome.isBlank() || nome.matches("\\d+")) {
                System.out.println("Erro: " + Mensagens.NOME_INVALIDO);
            } else {
                break;
            }
        }

        String email;

        while (true) {
            System.out.print(Mensagens.INPUT_EMAIL);
            email = lerEntradaOuSair(scanner);

            if (email.isBlank()) {
                System.out.println("Erro: " + Mensagens.EMAIL_INVALIDO);
                continue;
            }

            try {
                service.criarUsuario(new Usuario(nome, email));
                break;
            } catch (DadosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.println(Mensagens.CLIENTE_CADASTRADO_SUCESSO);
    }

    private void listarUsuarios(UsuarioService service) {
        var usuarios = service.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println(Mensagens.SEM_CLIENTES);
            return;
        }

        System.out.println(Mensagens.CLIENTES_CADASTRADOS);
        usuarios.forEach(System.out::println);
    }

    private void buscarUsuario(Scanner scanner, UsuarioService service) {

        while (true) {
            try {
                System.out.print(Mensagens.ID);
                long id = Long.parseLong(lerEntradaOuSair(scanner));

                Usuario usuario = service.buscarPorId(id);
                System.out.println(usuario);
                break;

            } catch (NumberFormatException e) {
                System.out.println(Mensagens.DIGITE_APENAS_NUMEROS);
            } catch (UsuarioNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void atualizarUsuario(Scanner scanner, UsuarioService service) {

        Usuario usuarioExistente;

        while (true) {
            try {
                System.out.print(Mensagens.ID);
                long id = Long.parseLong(lerEntradaOuSair(scanner));
                usuarioExistente = service.buscarPorId(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println(Mensagens.DIGITE_APENAS_NUMEROS);
            } catch (UsuarioNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        String nome;

        while (true) {
            System.out.print(Mensagens.NOVO_NOME);
            nome = lerEntradaOuSair(scanner);

            if (nome.isBlank() || nome.matches("\\d+")) {
                System.out.println("Erro: " + Mensagens.NOME_INVALIDO);
            } else {
                break;
            }
        }

        String email;

        while (true) {
            System.out.print(Mensagens.NOVO_EMAIL);
            email = lerEntradaOuSair(scanner);

            try {
                usuarioExistente.setNome(nome);
                usuarioExistente.setEmail(email);
                service.atualizarUsuario(usuarioExistente);
                break;
            } catch (DadosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.println(Mensagens.CLIENTE_ATUALIZADO);
    }

    private void removerUsuario(Scanner scanner, UsuarioService service) {

        while (true) {
            try {
                System.out.print(Mensagens.ID);
                long id = Long.parseLong(lerEntradaOuSair(scanner));

                service.removerUsuario(id);
                System.out.println(Mensagens.CLIENTE_REMOVIDO);
                break;

            } catch (NumberFormatException e) {
                System.out.println(Mensagens.DIGITE_APENAS_NUMEROS);
            } catch (UsuarioNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
