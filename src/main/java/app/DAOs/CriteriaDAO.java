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
    ObservableList<CriteriaModel> criteriaList = FXCollections.observableArrayList();

    public int createCriteria(String name, String description) {
        String sql = String.format("INSERT INTO criterio (nome, descricao) VALUES ('%s', '%s')", name, description);
        int criteriaId = 0;

        try {
            criteriaId = DatabaseConnection.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createCriteria: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return criteriaId;
    }

    public ObservableList<CriteriaModel> selectPeriodCriterias(int selectedPeriodId) {
        String sql = "SELECT * FROM criterio ORDER BY nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nome");
                String description = resultSet.getString("descricao");
                Timestamp timestamp = resultSet.getTimestamp("deleted_at");
                LocalDateTime deletedAt = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                String checkAssociationSql = String.format("SELECT COUNT(*) FROM criterio_periodo cp " +
                        "JOIN periodo p ON cp.periodo_id = p.id " +
                        "WHERE cp.criterio_id = %d AND p.id = %d AND cp.deleted_at IS NULL", id, selectedPeriodId);

                CriteriaModel criteria = new CriteriaModel(id, name, description, deletedAt);

                try(ResultSet checkResult = DatabaseConnection.executeQuery(checkAssociationSql)) {
                    if (checkResult.next() && checkResult.getInt(1) > 0) {
                        criteria.setDeletedAt(LocalDateTime.now());
                    }
                } catch (SQLException e) {
                    System.out.println("Erro no segundo SQL de selectPeriodCriterias: " + e.getMessage());
                }
                criteriaList.add(criteria);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectPeriodCriterias: " + e.getMessage());
        }
        return criteriaList;
    }

    public void updateCriteriaToPeriod(int selectedPeriodId, ObservableList<CriteriaModel> criteriaList) {
        String sqlSelectPeriodoId = String.format("SELECT id FROM periodo WHERE id = %d", selectedPeriodId);
        int periodId = 0;

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sqlSelectPeriodoId)) {
            while (resultSet.next()) {
                periodId = resultSet.getInt("id");

                for (CriteriaModel criteria : criteriaList) {
                    int criteriaId = criteria.getId();
                    boolean isDeleted = criteria.getDeletedAt() == null;
                    String deletedAtValue = isDeleted ? "'" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "'" : "NULL" ;

                    String sqlCheckExistence = String.format("SELECT COUNT(*) FROM criterio_periodo WHERE criterio_id = %d AND periodo_id = %d", criteriaId, periodId);
                    String sqlInsertOrUpdate = String.format("INSERT INTO criterio_periodo (criterio_id, periodo_id, deleted_at) VALUES (%d, %d, %s) " +
                            "ON DUPLICATE KEY UPDATE deleted_at = %s", criteriaId, periodId, deletedAtValue, deletedAtValue);

                    try (ResultSet checkResultSet = DatabaseConnection.executeQuery(sqlCheckExistence)) {
                        if (checkResultSet.next() && checkResultSet.getInt(1) > 0) {
                            try {
                                DatabaseConnection.executeUpdate(sqlInsertOrUpdate);
                            } catch (SQLException e) {
                                System.out.println("Erro no terceiro SQL de updateCriteriaToPeriod: " + e.getMessage());
                            }
                        } else {
                            try {
                                DatabaseConnection.executeUpdate(sqlInsertOrUpdate);
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
