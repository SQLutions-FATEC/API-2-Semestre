package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SprintDAO {
    private ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

    public ObservableList<SprintModel> selectSprints(int selectedPeriodId) {
        String sql = "SELECT * FROM sprint s WHERE s.periodo = ? ORDER BY s.data_inicio";
        sprintList.clear();

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, selectedPeriodId)) {
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

    public ObservableList<SprintModel> selectLast4Sprints() {
        String sql = "SELECT * FROM sprint s ORDER BY s.data_inicio DESC LIMIT 4";
        sprintList.clear();

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                SprintModel sprint = new SprintModel(id, description, dataInicio, dataFim);
                sprintList.add(sprint);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectLast4Sprints: " + e.getMessage());
        }
        return sprintList;
    }

    public boolean createSprint(String descricao, Date dataInicio, Date dataFim) {
        String getPeriodoSql = "SELECT id FROM periodo WHERE ano = ? AND semestre = ?";
        String insertSprintSql = "INSERT INTO sprint (descricao, data_inicio, data_fim, periodo) VALUES (?, ?, ?, ?)";

        try {
            int semestre = (dataInicio.getMonth() + 1 <= 6) ? 1 : 2;
            int ano = dataInicio.getYear() + 1900;
            int periodoId;

            try (ResultSet rs = DatabaseConnection.executeQuery(getPeriodoSql, ano, semestre)) {
                if (rs.next()) {
                    periodoId = rs.getInt("id");
                } else {
                    throw new SQLException("Período não encontrado para ano " + ano + " e semestre " + semestre);
                }
            }

            int rowsAffected = DatabaseConnection.executeUpdate(insertSprintSql, descricao, dataInicio, dataFim, periodoId);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao criar sprint: " + e.getMessage());
            return false;
        }
    }

}
