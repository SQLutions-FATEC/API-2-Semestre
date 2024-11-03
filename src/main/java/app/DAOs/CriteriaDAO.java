package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.CriteriaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CriteriaDAO {
    ObservableList<CriteriaModel> criteriaList = FXCollections.observableArrayList();

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
                }
                criteriaList.add(criteria);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectCriteria: " + e.getMessage());
        }
        return criteriaList;
    }
}
