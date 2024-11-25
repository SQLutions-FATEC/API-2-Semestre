package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.ScoreModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class ScoreDAO {
    public int createScore(int score, int sprintId, int teamId) {
        String sql = "INSERT INTO pontuacao (valor, sprint, equipe, data) VALUES (?, ?, ?, ?)";
        int generatedKey = 0;

        LocalDate currentDate = LocalDate.now();

        try {
            generatedKey = DatabaseConnection.executeUpdate(sql, score, sprintId, teamId, currentDate);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createScore: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }

    public ScoreModel selectScoreBySprintId(int teamId, int sprintId) {
        String sql = "SELECT * FROM pontuacao WHERE equipe = ? AND sprint = ?";
        ScoreModel score = null;

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, teamId, sprintId)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int value = resultSet.getInt("valor");
                int sprint = resultSet.getInt("sprint");
                int team = resultSet.getInt("equipe");
                Date date = resultSet.getDate("data");

                score = new ScoreModel(id, value, sprint, team, date);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectScoreDateBySprintId: " + e.getMessage());
        }
        return score;
    }
}
