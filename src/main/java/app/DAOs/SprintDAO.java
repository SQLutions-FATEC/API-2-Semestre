package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class SprintDAO {
    ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

    public ObservableList<SprintModel> selectSprints(int selectedPeriodId) {
        String sql = "SELECT * FROM sprint s WHERE s.periodo = ? ORDER BY s.data_inicio";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, selectedPeriodId)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                SprintModel sprint = new SprintModel(id, description, dataInicio, dataFim);
                sprintList.add(sprint);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectSprints: " + e.getMessage());
        }
        return sprintList;
    }

    public int selectCurrentSprintId() {
        LocalDate currentDate = LocalDate.now();

        String sql = "SELECT s.id FROM sprint s WHERE ? BETWEEN s.data_inicio AND s.data_fim";
        int id = 0;

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, currentDate)) {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectSprints: " + e.getMessage());
        }
        return id;
    }
}
