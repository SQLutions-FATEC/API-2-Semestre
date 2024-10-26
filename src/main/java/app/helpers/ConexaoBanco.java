package app.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.models.NotaModel;
import io.github.cdimascio.dotenv.Dotenv;

public class ConexaoBanco {
    static Dotenv dotenv = Dotenv.load();

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/avaliador";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida com o banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }
}

public class NotaDAO {

    public List<NotaModel> buscarNotas(int idEquipe, int idSemestre, int idSprint,Integer idAluno) {
        List<NotaModel> notas = new ArrayList<>();
        String sql = "SELECT id_aluno, id_criterio, AVG(nota) AS media_nota" +
                     "FROM Nota" +
                     "WHERE id_equipe = ? AND id_semestre = ? AND id_sprint = ?" +
                     (idAluno != null ? "AND id_aluno = ? ":"") +
                     "GROUP BY id_aluno, id_criterio";

        try (Connection connection = conexao.Banco.getConnection();
              preparedStatement statement = connection.prepareStatement (sql)) {

             statement.setInt(1, idEquipe);
             statement.setInt(2, idSemestre);
             if (idAluno != null) {
                 statement.setInt(4, idAluno);
             }

             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()){
                 int alunoId = resultSet.getInt("id_aluno");
                 int criterioId = resultSet.getInt("id_criterio");
                 double mediaNota = resultSet.getDouble("media_nota");

                 notas.add(new NotaModel(alunoId, criterioId, mediaNota));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }
}
