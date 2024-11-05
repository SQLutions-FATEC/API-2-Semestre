package app.DAOs;

import app.helpers.DatabaseConnection;

import java.sql.SQLException;

public class SetScoreDAO {
    public int createScore(int score, int sprintId, int teamId) {
        String sql = "INSERT INTO pontuacao (valor, sprint, equipe) VALUES (?, ?, ?)";
        int generatedKey = 0;

        try {
            generatedKey = DatabaseConnection.executeUpdate(sql, score, sprintId, teamId);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createScore: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }
}
