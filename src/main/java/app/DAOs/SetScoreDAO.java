package app.DAOs;

import app.helpers.DatabaseConnection;

import java.sql.SQLException;
import java.time.LocalDate;

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
}
