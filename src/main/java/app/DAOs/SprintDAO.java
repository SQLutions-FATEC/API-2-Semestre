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
    public ObservableList<SprintModel> selectSprints(int selectedPeriodId) {
        ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM sprint s WHERE s.periodo = ? AND deleted_at IS NULL ORDER BY s.data_inicio";

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

    public ObservableList<SprintModel> selectLast8Sprints() {
        ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

        String sql = """
        SELECT 
            s.id,
            CONCAT(p.ano, '.', p.semestre, ' - ', 
                   TRIM(SUBSTRING(s.descricao, LOCATE(' ', s.descricao) + 1))) AS sprint_description, 
            s.data_inicio,
            s.data_fim
        FROM sprint s
        INNER JOIN periodo p ON s.periodo = p.id
        WHERE s.deleted_at IS NULL
        ORDER BY s.data_fim DESC
        LIMIT 8
    """;

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("sprint_description");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                SprintModel sprint = new SprintModel(id, description, dataInicio, dataFim);
                sprintList.add(sprint);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectLast8Sprints: " + e.getMessage());
        }
        return sprintList;
    }

    public boolean createSprint(String descricao, Date dataInicio, Date dataFim) throws SQLException {
        if (!dataFim.after(dataInicio)) {
            throw new SQLException("A data de término deve ser posterior à data de início.");
        }

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

        if (isDuplicateSprint(descricao, periodoId)) {
            throw new SQLException("Já existe uma sprint com esse nome para o mesmo período.");
        }

        if (!isSequentialDates(dataInicio, periodoId)) {
            throw new SQLException("A sequência de datas da sprint está fora de ordem.");
        }

        int rowsAffected = DatabaseConnection.executeUpdate(insertSprintSql, descricao, dataInicio, dataFim, periodoId);
        return rowsAffected > 0;
    }


    private boolean isDateRangeAvailable(Date dataInicio, Date dataFim, int periodoId) {
        ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

        String sql = """
        SELECT COUNT(*) AS count 
        FROM sprint 
        WHERE periodo = ? AND deleted_at IS NULL
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

    private boolean isDuplicateSprint(String descricao, int periodoId) {
        String sql = "SELECT COUNT(*) AS count FROM sprint WHERE descricao = ? AND periodo = ? AND deleted_at IS NULL";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, descricao, periodoId)) {
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar duplicidade da sprint: " + e.getMessage());
        }
        return false;
    }

    private boolean isSequentialDates(Date dataInicio, int periodoId) {
        String sql = """
            SELECT data_fim 
            FROM sprint 
            WHERE periodo = ? AND deleted_at IS NULL 
            ORDER BY data_fim DESC 
            LIMIT 1
        """;

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, periodoId)) {
            if (resultSet.next()) {
                Date lastEndDate = resultSet.getDate("data_fim");
                return lastEndDate.before(dataInicio);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar sequência das datas: " + e.getMessage());
        }

        return true;
    }

    public SprintModel selectPastSprint() {
        LocalDate currentDate = LocalDate.now();

        String sql = "SELECT s.* FROM sprint s WHERE s.data_fim < ? ORDER BY s.data_fim DESC LIMIT 1";

        SprintModel sprint = null;

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, currentDate)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date startDate = resultSet.getDate("data_inicio");
                Date endDate = resultSet.getDate("data_fim");

                sprint = new SprintModel(id, description, startDate, endDate);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectPastSprint: " + e.getMessage());
        }
        return sprint;
    }

    public int deleteSprint(int sprintId) throws SQLException {
        int generatedKey = 0;
        String sql = "UPDATE sprint SET deleted_at = NOW() where id = ?";
        try{
            generatedKey = DatabaseConnection.executeUpdate(sql, sprintId);
        } catch (SQLException e){
            System.out.println("Erro no SQL de deleteSprintt: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }

}
