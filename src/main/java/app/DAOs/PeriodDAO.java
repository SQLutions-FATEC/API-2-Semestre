package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.PeriodModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodDAO {
    ObservableList<PeriodModel> periodList = FXCollections.observableArrayList();

    public ObservableList<PeriodModel> selectPeriods() {
        String sql = "SELECT * FROM periodo ORDER BY ano";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int semester = resultSet.getInt("semestre");
                int year = resultSet.getInt("ano");

                PeriodModel period = new PeriodModel(id, semester, year);
                periodList.add(period);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectPeriods: " + e.getMessage());
        }
        return periodList;
    }
}
