package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.TeamModel;
import app.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDAO {
    ObservableList<TeamModel> teamList = FXCollections.observableArrayList();
    int generatedKey = 0;

    public int createTeam(String teamName, String teamGithub) {
        String sql = String.format("INSERT INTO equipe (nome, github) VALUES ('%s', '%s')", teamName, teamGithub);

        try {
            generatedKey = DatabaseConnection.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createTeam: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }

    public ObservableList<TeamModel> selectTeams() {
        String sql = "SELECT e.id, e.nome, e.github FROM equipe e ORDER BY e.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                String github = resultSet.getString("github");

                TeamModel team = new TeamModel(id, name, github);
                teamList.add(team);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectTeams: " + e.getMessage());
        }
        return teamList;
    }

    public ObservableList<TeamModel> selectTeamsByPeriod(int periodId) {
        String sql = String.format("SELECT e.id, e.nome, e.github FROM equipe e JOIN equipe_periodo ep ON ep.equipe_id = e.id WHERE ep.periodo_id = %d ORDER BY e.nome", periodId);

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                String github = resultSet.getString("github");

                TeamModel team = new TeamModel(id, name, github);
                teamList.add(team);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectTeamsByPeriod: " + e.getMessage());
        }
        return teamList;
    }
}