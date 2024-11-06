package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.TeamModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TeamDAO {
    ObservableList<TeamModel> teamList = FXCollections.observableArrayList();
    Map<Integer, String> teamNamesMap = new HashMap<>();

    public ObservableList<TeamModel> fetchTeams() {
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
            System.out.println("Erro no SQL de fetchTeams: " + e.getMessage());
        }
        return teamList;
    }

    public Map<Integer, String> fetchTeamNames() {
        String sql = "SELECT id, nome FROM equipe";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                Integer teamId = resultSet.getInt("id");
                String teamName = resultSet.getString("nome");
                teamNamesMap.put(teamId, teamName);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar nomes das equipes: " + e.getMessage());
        }
        return teamNamesMap;
    }

}