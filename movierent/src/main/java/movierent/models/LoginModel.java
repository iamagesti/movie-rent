package movierent.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import movierent.entities.UserEntity;

public class LoginModel {
    private DataSource dataSource;

    public LoginModel(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public boolean CheckUserExist(String username){
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
    public boolean UserPasswordValid(UserEntity user){
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
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
    public Integer GetRoleId(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Integer role_id = resultSet.getInt("roles_id");
                resultSet.close();
                return role_id;
            } else {
                resultSet.close();
                return -1;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
