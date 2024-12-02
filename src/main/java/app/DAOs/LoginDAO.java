package app.DAOs;

import app.helpers.DatabaseConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class LoginDAO {
    public void executeSQLFromFile(String filePath) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseConnection.getConnection(false);
            statement = connection.createStatement();

            InputStream inputStream = getClass().getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo SQL não encontrado: " + filePath);
            }

            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            String[] commands = sql.split(";");

            for (String command : commands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command.trim());
                }
            }
        } catch (Exception e) {
            throw new SQLException("Erro ao executar o arquivo SQL", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
}
