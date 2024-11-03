package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SprintDAO {
    ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

    public ObservableList<SprintModel> selectSprint(int selectedPeriodId) {
        String sql = String.format("SELECT * FROM sprint s WHERE s.periodo = '%d' ORDER BY s.data_inicio", selectedPeriodId);

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                SprintModel sprint = new SprintModel(id, description, dataInicio, dataFim);
                sprintList.add(sprint);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectTeams: " + e.getMessage());
        }
        return sprintList;
    }
}
