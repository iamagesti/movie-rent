package movierent.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import movierent.entities.UserEntity;

public class RegisterModel {
    private DataSource dataSource;

    public RegisterModel(DataSource dataSource) {
        this.dataSource = dataSource;

    }
    public boolean CheckUserExist(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resultSet.close();
                return true;
            } else {
                resultSet.close();
                return false;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void CreateUser(UserEntity user) {
        String sql = "INSERT INTO users (username, password, roles_id) VALUES (?, ?, ?)";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRoleid());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
