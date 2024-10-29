package app.helpers;

import app.models.NotaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public List<NotaModel> buscarNotas(int idEquipe, int idSemestre, int idSprint, Integer idAluno) {
        List<NotaModel> notas = new ArrayList<>();
        String sql = "SELECT id_aluno, id_criterio, AVG(nota) AS media_nota" +
                "FROM Nota" +
                "WHERE id_equipe = ? AND id_semestre = ? AND id_sprint = ?" +
                (idAluno != null ? "AND id_aluno = ? " : "") +
                "GROUP BY id_aluno, id_criterio";

        try (Connection connection = ConexaoBanco.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idEquipe);
            statement.setInt(2, idSemestre);
            if (idAluno != null) {
                statement.setInt(4, idAluno);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
