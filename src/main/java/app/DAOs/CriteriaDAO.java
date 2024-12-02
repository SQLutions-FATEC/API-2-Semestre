package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.CriteriaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CriteriaDAO {
    public int createCriteria(String name, String description) {
        String sql = "INSERT INTO criterio (nome, descricao) VALUES (?, ?)";
        int criteriaId = 0;

        try {
            criteriaId = DatabaseConnection.executeUpdate(sql, name, description);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createCriteria: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return criteriaId;
    }

    public ObservableList<CriteriaModel> selectCriteriasByPeriodId(int selectedPeriodId) {
        ObservableList<CriteriaModel> criteriaList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM criterio ORDER BY nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int criterioId = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                String description = resultSet.getString("descricao");
                Timestamp timestamp = resultSet.getTimestamp("deleted_at");
                LocalDateTime deletedAt = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                String checkAssociationSql = "SELECT COUNT(*) FROM criterio_periodo cp " +
                        "JOIN periodo p ON cp.periodo_id = p.id " +
                        "WHERE cp.criterio_id = ? AND p.id = ? AND cp.deleted_at IS NULL";

                CriteriaModel criteria = new CriteriaModel(criterioId, name, description, deletedAt);

                try(ResultSet checkResult = DatabaseConnection.executeQuery(checkAssociationSql, criterioId, selectedPeriodId)) {
                    if (checkResult.next() && checkResult.getInt(1) > 0) {
                        criteria.setDeletedAt(LocalDateTime.now());
                    }
                } catch (SQLException e) {
                    System.out.println("Erro no segundo SQL de selectCriteriasByPeriod: " + e.getMessage());
                }
                criteriaList.add(criteria);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectCriteriasByPeriod: " + e.getMessage());
        }
        return criteriaList;
    }

    public ObservableList<CriteriaModel> selectActiveCriteriasByPeriod(int selectedPeriodId) {
        ObservableList<CriteriaModel> criteriaList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM criterio c JOIN criterio_periodo cp ON cp.criterio_id = c.id WHERE cp.periodo_id = ? AND c.deleted_at IS NULL AND cp.deleted_at IS NULL ORDER BY c.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, selectedPeriodId)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                String description = resultSet.getString("descricao");
                Timestamp timestamp = resultSet.getTimestamp("deleted_at");
                LocalDateTime deletedAt = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                CriteriaModel criteria = new CriteriaModel(id, name, description, deletedAt);

                criteriaList.add(criteria);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectActiveCriteriasByPeriod: " + e.getMessage());
        }
        return criteriaList;
    }

    public void updateCriteriaToPeriod(int selectedPeriodId, ObservableList<CriteriaModel> criteriaList) {
        String sqlSelectPeriodId = "SELECT id FROM periodo WHERE id = ?";
        int periodId = 0;

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sqlSelectPeriodId, selectedPeriodId)) {
            while (resultSet.next()) {
                periodId = resultSet.getInt("id");

                for (CriteriaModel criteria : criteriaList) {
                    int criteriaId = criteria.getId();
                    boolean isDeleted = criteria.getDeletedAt() == null;
                    String deletedAtValue = isDeleted ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

                    String sqlCheckExistence = "SELECT COUNT(*) FROM criterio_periodo WHERE criterio_id = ? AND periodo_id = ?";
                    String sqlInsertOrUpdate = "INSERT INTO criterio_periodo (criterio_id, periodo_id, deleted_at) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE deleted_at = ?";

                    try (ResultSet checkResultSet = DatabaseConnection.executeQuery(sqlCheckExistence, criteriaId, periodId)) {
                        if (checkResultSet.next() && checkResultSet.getInt(1) > 0) {
                            try {
                                DatabaseConnection.executeUpdate(sqlInsertOrUpdate, criteriaId, periodId, deletedAtValue, deletedAtValue);
                            } catch (SQLException e) {
                                System.out.println("Erro no terceiro SQL de updateCriteriaToPeriod: " + e.getMessage());
                            }
                        } else {
                            try {
                                DatabaseConnection.executeUpdate(sqlInsertOrUpdate, criteriaId, periodId, deletedAtValue, deletedAtValue);
                            } catch (SQLException e) {
                                System.out.println("Erro no quarto SQL de updateCriteriaToPeriod: " + e.getMessage());
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro no segundo SQL de updateCriteriaToPeriod: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de updateCriteriaToPeriod: " + e.getMessage());
        }
    }
}
