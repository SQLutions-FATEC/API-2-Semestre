package app.helpers;

import app.models.AverageGradeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AverageGradeDAO {
    private Map<String, AverageGradeModel> studentsMap = new HashMap<>();

    public Map<String, AverageGradeModel> fetchAverages(int teamId, int periodId, int sprintId) {
        String sql = String.format(
                "SELECT u.nome AS usuario_nome, c.nome AS criterio, AVG(n.valor) AS media_nota FROM nota n " +
                        "JOIN usuario u ON u.id = n.avaliado " +
                        "JOIN periodo p ON p.id = n.periodo " +
                        "JOIN sprint s ON s.id = n.sprint " +
                        "JOIN criterio c ON n.criterio = c.id " +
                        "WHERE p.id = '%d' AND s.id = '%d' AND u.equipe = '%d' " +
                        "GROUP BY u.nome, c.nome", periodId, sprintId, teamId);

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                String studentName = resultSet.getString("usuario_nome");
                String criteria = resultSet.getString("criterio");
                Double averageGrade = resultSet.getDouble("media_nota");

                AverageGradeModel student = studentsMap.getOrDefault(studentName, new AverageGradeModel(studentName));
                student.setAverage(criteria, averageGrade);
                studentsMap.put(studentName, student);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de fetchAverages: " + e.getMessage());
        }
        return studentsMap;
    }
}