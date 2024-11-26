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
        String sql = "SELECT * FROM sprint s ORDER BY s.data_hora DESC LIMIT 4";
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

    public boolean createSprint(String descricao, Date dataInicio, Date dataFim) throws SQLException {
        String getPeriodoSql = "SELECT id FROM periodo WHERE ano = ? AND semestre = ?";
        String insertSprintSql = "INSERT INTO sprint (descricao, data_inicio, data_fim, periodo) VALUES (?, ?, ?, ?)";

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
        if (!isDateRangeAvailable(dataInicio, dataFim, periodoId)) {
            throw new SQLException("Datas inseridas já estão em uso.");
        }

        int rowsAffected = DatabaseConnection.executeUpdate(insertSprintSql, descricao, dataInicio, dataFim, periodoId);
        return rowsAffected > 0;
    }


    private boolean isDateRangeAvailable(Date dataInicio, Date dataFim, int periodoId) {
        String sql = """
        SELECT COUNT(*) AS count 
        FROM sprint 
        WHERE periodo = ? 
        AND (
            (data_inicio <= ? AND data_fim >= ?) OR
            (data_inicio <= ? AND data_fim >= ?) OR
            (data_inicio >= ? AND data_fim <= ?)
        )
    """;

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, periodoId, dataFim, dataFim, dataInicio, dataInicio, dataInicio, dataFim)) {
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count == 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar disponibilidade do intervalo de datas: " + e.getMessage());
        }

        return false;
    }


    public SprintModel selectPastSprint() {
        LocalDate currentDate = LocalDate.now();

        String sql = "SELECT s.* FROM sprint s WHERE s.data_fim < ? ORDER BY s.data_fim DESC LIMIT 1";

        SprintModel sprint = null;

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, currentDate)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date startDate = resultSet.getDate("data_inicio");
                Date endDate = resultSet.getDate("data_fim");

                sprint = new SprintModel(id, description, startDate, endDate);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectSprints: " + e.getMessage());
        }
        return sprint;
    }



}
