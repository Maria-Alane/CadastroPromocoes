package org.example.repository;


import org.example.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {


    private static final String URL = "jdbc:h2:mem:usuarios_db;DB_CLOSE_DELAY=-1";

    private static final String USER = "sa";

    private static final String PASSWORD = "";


    public UsuarioRepository() {
        criarTabela();
    }


    private void criarTabela() {

        String sql = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public void salvar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Usuario> listarTodos() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Usuario usuario = new Usuario(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email")
                );

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }


    public Usuario buscarPorId(long id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Usuario(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setLong(3, usuario.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean remover(long id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int linhas = statement.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
