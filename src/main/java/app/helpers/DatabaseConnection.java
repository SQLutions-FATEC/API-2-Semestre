package app.helpers;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    static Dotenv dotenv = Dotenv.load();

    private static final String DEFAULT_SCHEMA = "avaliador";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    private static Connection connection = null;

    public static Connection getConnection(boolean useDefaultSchema) throws SQLException {
        if (connection == null || connection.isClosed()) {
            String URL = "jdbc:mysql://127.0.0.1:3306/";
            if (useDefaultSchema) {
                URL = URL + DEFAULT_SCHEMA + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            }
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Método para SELECT
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        try {
            Connection conn = getConnection(true);
            PreparedStatement statement = conn.prepareStatement(sql);
            setParameters(statement, params);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao executar consulta: " + e.getMessage());
            throw e;
        }
    }

    // Método para INSERT, UPDATE e DELETE
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try {
            Connection conn = getConnection(true);
            PreparedStatement statement = conn.prepareStatement(sql);
            setParameters(statement, params);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao executar atualização: " + e.getMessage());
            throw e;
        }
    }

    private static void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int index = 0; index < params.length; index++) {
            statement.setObject(index + 1, params[index]);
        }
    }

    public static void closeResources() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}
