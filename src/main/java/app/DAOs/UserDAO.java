package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    ObservableList<UserModel> studentList = FXCollections.observableArrayList();
    int generatedKey = 0;

    public void createStudents(ObservableList <app. models. UserModel> students, int teamId) {
        String sql = "INSERT INTO usuario (ra, nome, senha, email, tipo, equipe) VALUES (?, ?, ?, ?, ?, ?)";

        int typeStudent = 2;

        for (UserModel student : students) {
            try {
                DatabaseConnection.executeUpdate(sql, student.getRa(), student.getNome(), student.getSenha(), student.getEmail(), typeStudent, teamId);
            } catch (SQLException e) {
                System.out.println("Erro no SQL de createStudents: " + e.getMessage());
            } finally {
                DatabaseConnection.closeResources();
            }
        }
    }
    
    public ObservableList<UserModel> selectStudents() {
        String sql = "SELECT u.ra, u.nome, u.email, u.senha, u.equipe FROM usuario u WHERE u.ra IS NOT NULL AND deleted_at IS NULL ORDER BY u.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int ra = resultSet.getInt("ra");
                String name = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String password = resultSet.getString("senha");
                int teamId = resultSet.getInt("equipe");

                UserModel user = new UserModel(ra, name, email, password, teamId);
                studentList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectStudents: " + e.getMessage());
        }
        return studentList;
    }

    public int deleteStudent(int ra) {
        String sql = "UPDATE usuario SET deleted_at = NOW() WHERE ra = ?";
        try {
            generatedKey = DatabaseConnection.executeUpdate(sql, ra);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de deleteStudent: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }

    public int updateStudentTeam(int ra, int teamId) {
        String sql = "UPDATE usuario SET equipe = ? WHERE ra = ?";
        try {
            generatedKey = DatabaseConnection.executeUpdate(sql, teamId, ra);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de updateStudentTeam: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return generatedKey;
    }
}