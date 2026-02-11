package org.example.repository;

import org.example.model.Usuario;
import org.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private static final Logger logger =
            Logger.getLogger(UsuarioRepositoryImpl.class.getName());

    public UsuarioRepositoryImpl() {
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

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void salvar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Connection connection = DatabaseConnection.getConnection();
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
            logger.severe(e.getMessage());
        }

        return usuarios;
    }


    @Override
    public Usuario buscarPorId(long id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
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
            logger.severe(e.getMessage());
        }

        return null;
    }


    @Override
    public void atualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setLong(3, usuario.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public boolean remover(long id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        return false;
    }
}
