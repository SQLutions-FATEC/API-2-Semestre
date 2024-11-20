package app.DAOs;

import app.helpers.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class SetScoreDAO {
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

    public Date selectScoreDateBySprintId(int teamId, int sprintId) {
        String sql = "SELECT data FROM pontuacao WHERE equipe = ? AND sprint = ?";
        Date date = null;

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, teamId, sprintId)) {
            while (resultSet.next()) {
                date = resultSet.getDate("data");
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectScoreDateBySprintId: " + e.getMessage());
        }
        return date;
    }
}
