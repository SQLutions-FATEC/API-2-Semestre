package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<Integer> createStudents(ObservableList <UserModel> students, int teamId) {
        String checkSql = "SELECT id FROM usuario WHERE ra = ?";
        String sql = "INSERT INTO usuario (ra, nome, senha, email, tipo, equipe) VALUES (?, ?, ?, ?, ?, ?)";
        List<Integer> generatedKeys = new ArrayList<>();
        int generatedKey = 0;
        int typeStudent = 2;

        for (UserModel student : students) {
            try {
                ResultSet resultSet = DatabaseConnection.executeQuery(checkSql, student.getRa());
                if (resultSet.next()) {
                    continue;
                }
                generatedKey = DatabaseConnection.executeUpdate(sql, student.getRa(), student.getNome(), student.getSenha(), student.getEmail(), typeStudent, teamId);
            } catch (SQLException e) {
                System.out.println("Erro no SQL de createStudents: " + e.getMessage());
            } finally {
                DatabaseConnection.closeResources();
            }
            generatedKeys.add(generatedKey);
        }

        return generatedKeys;
    }

    public UserModel selectUserByLogin(String userEmail, String userPassword) {
        UserModel user = null;
        String sql = "SELECT id, ra, nome, email, senha, equipe FROM usuario WHERE email = ? AND senha = ? AND deleted_at IS NULL";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, userEmail, userPassword)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ra = resultSet.getInt("ra");
                String name = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String password = resultSet.getString("senha");
                int teamId = resultSet.getInt("equipe");

                user = new UserModel(id, ra, name, email, password, teamId);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectUsers: " + e.getMessage());
        }
        return user;
    }

    public ObservableList<UserModel> selectStudents() {
        ObservableList<UserModel> studentList = FXCollections.observableArrayList();
        String sql = "SELECT u.id, u.ra, u.nome, u.email, u.senha, u.equipe FROM usuario u WHERE u.ra IS NOT NULL AND deleted_at IS NULL ORDER BY u.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ra = resultSet.getInt("ra");
                String name = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String password = resultSet.getString("senha");
                int teamId = resultSet.getInt("equipe");

                UserModel user = new UserModel(id, ra, name, email, password, teamId);
                studentList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectStudents: " + e.getMessage());
        }
        return studentList;
    }

    public ObservableList<UserModel> selectStudentsByTeamId(int userTeamId) {
        ObservableList<UserModel> studentList = FXCollections.observableArrayList();
        String sql = "SELECT u.* FROM usuario u WHERE u.ra IS NOT NULL AND u.equipe = ? AND deleted_at IS NULL ORDER BY u.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, userTeamId)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ra = resultSet.getInt("ra");
                String name = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String password = resultSet.getString("senha");
                int teamId = resultSet.getInt("equipe");

                UserModel user = new UserModel(id, ra, name, email, password, teamId);
                studentList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectStudentsByTeamId: " + e.getMessage());
        }
        return studentList;
    }

    public int deleteStudent(int ra) {
        int generatedKey = 0;
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
        int generatedKey = 0;
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