package app.helpers;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    static Dotenv dotenv = Dotenv.load();

    private static final String DEFAULT_SCHEMA = "avaliador";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection(boolean useDefaultSchema) throws SQLException {
        String baseUrl = "jdbc:mysql://127.0.0.1:3306/";
        String url = baseUrl;

        if (useDefaultSchema) {
            url += DEFAULT_SCHEMA;
        }

        url += "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=America/Sao_Paulo&useLegacyDatetimeCode=false";

        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }


    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection connection = getConnection(true);
        PreparedStatement statement = connection.prepareStatement(sql);
        setParameters(statement, params);
        return statement.executeQuery();
    }

    private static void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int index = 0; index < params.length; index++) {
            statement.setObject(index + 1, params[index]);
        }
    }

    public static void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
}


