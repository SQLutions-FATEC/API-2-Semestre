package app.DAOs;

import app.helpers.DatabaseConnection;

import java.sql.SQLException;

public class SetScoreDAO {
    public int createScore(int score, int sprintId, int teamId) {
        String sql = String.format("INSERT INTO pontuacao (valor, sprint, equipe) VALUES ('%d', '%d', '%d')", score, sprintId, teamId);
        int generatedKey = 0;

        try {
            generatedKey = DatabaseConnection.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createScore: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }
}
