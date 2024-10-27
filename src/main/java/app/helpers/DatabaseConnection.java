package app.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    static Dotenv dotenv = Dotenv.load();

    private static final String DEFAULT_SCHEMA = "avaliador";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");;

    public static Connection getConnection(boolean useDefaultSchema) throws SQLException {
        String URL = "jdbc:mysql://127.0.0.1:3306/";

        if (useDefaultSchema) {
            URL = URL + DEFAULT_SCHEMA;
        }
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida com o banco de dados!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }
}
