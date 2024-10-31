package app.helpers;

import app.models.NotaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public List<NotaModel> buscarNotas(int idEquipe, int semestre, int ano, int idSprint) {
        List<NotaModel> notas = new ArrayList<>();

        String sql = "SELECT n.id, u.equipe, u.nome, n.valor, p.semestre, p.ano, s.descricao " +
                "FROM nota n " +
                "JOIN usuario u ON u.id = n.avaliado " +
                "JOIN periodo p ON p.id = n.periodo " +
                "JOIN sprint s ON s.id = n.sprint " +
                "WHERE p.semestre = ? AND p.ano = ? AND s.id = ? AND u.equipe = ?";

        try (Connection connection = DatabaseConnection.getConnection(true);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Definir os parâmetros da query
            statement.setInt(1, semestre);
            statement.setInt(2, ano);
            statement.setInt(3, idSprint);
            statement.setInt(4, idEquipe);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int equipe = resultSet.getInt("equipe");
                String nome = resultSet.getString("nome");
                double valor = resultSet.getDouble("valor");
                int semestreResult = resultSet.getInt("semestre");
                int anoResult = resultSet.getInt("ano");
                String descricao = resultSet.getString("descricao");

                notas.add(new NotaModel(id, equipe, nome, valor, semestreResult, anoResult, descricao));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notas;
    }
}