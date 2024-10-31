package app.helpers;

import app.models.AverageGradeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AverageGradeDAO {

    public List<AverageGradeModel> fetchAverages(int teamId, int periodId, int sprintId) {
        List<AverageGradeModel> notas = new ArrayList<>();

        String sql = String.format("SELECT u.nome AS userName, AVG(n.valor) AS gradeAverage, c.nome AS criteriaName FROM nota n JOIN usuario u ON u.id = n.avaliado JOIN periodo p ON p.id = n.periodo JOIN sprint s ON s.id = n.sprint WHERE p.id = '%d' AND s.id = '%d' AND u.equipe = '%d' GROUP BY u.equipe, u.nome, p.semestre, p.ano, s.descricao;", periodId, sprintId, teamId);

        try (Connection connection = DatabaseConnection.getConnection(true);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                double gradeAverage = resultSet.getDouble("gradeAverage");
                String criteriaName = resultSet.getString("criteriaName");

//                notas.add(new AverageGradeModel(userName, gradeAverage, criteriaName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }
}