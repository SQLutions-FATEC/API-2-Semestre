package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    ObservableList<UserModel> studentList = FXCollections.observableArrayList();
    int rowsAffected = 0;
    
    public ObservableList<UserModel> selectStudents() {
        String sql = "SELECT u.ra, u.nome, u.email, u.senha, u.equipe FROM usuario u WHERE u.ra IS NOT NULL AND deleted_at IS NULL ORDER BY u.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                Integer ra = resultSet.getInt("ra");
                String name = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String password = resultSet.getString("senha");
                Integer teamId = resultSet.getInt("equipe");

                UserModel user = new UserModel(ra, name, email, password, teamId);
                studentList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de studentList: " + e.getMessage());
        }
        return studentList;
    }

    public int deleteStudent(int ra) {
        String sql = String.format(
                "UPDATE usuario SET deleted_at = NOW() WHERE ra = '%d'", ra);
        try {
            rowsAffected = DatabaseConnection.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de studentList: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return rowsAffected;
    }

    public int updateStudentTeam(int ra, int teamId) {
        String sql = String.format(
                "UPDATE usuario SET equipe = '%d' WHERE ra = '%d'", teamId, ra);
        try {
            rowsAffected = DatabaseConnection.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de studentList: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return rowsAffected;
    }
}