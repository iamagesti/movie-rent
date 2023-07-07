package movierent.models;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import movierent.entities.UserEntity;
import movierent.utils.PrintUtils;

public class UserModel {
    private DataSource dataSource;
    private PrintUtils printUtils;

    public UserModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void ChangePassword(UserEntity user) {
        String sql = "Update users SET password = ? where username = ?";

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public UserEntity[] findAllUser() {
        String sql = "select a.username,b.name as role_name from users a,roles b where a.roles_id = b.roles_id ";
        printUtils = new PrintUtils(dataSource);
        try (
                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                ) {
            ResultSet resultSet = stmt.executeQuery(sql);       
            List<UserEntity> list = new ArrayList<>();
            while (resultSet.next()) {
                UserEntity user = new UserEntity();
                user.setUsername(resultSet.getString("username"));
                user.setRoleName(resultSet.getString("role_name"));
                list.add(user);
            }
            resultSet = stmt.executeQuery(sql);
            printUtils.PrintResult(resultSet);
            return list.toArray(new UserEntity[] {});
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void EditUser(String NewUsername, String Password, String Username, String Role) {
        Integer role_id = -1;
        if (Role.toLowerCase().equals("admin")){
            role_id = 0;
        } else if (Role.toLowerCase().equals("user")) {
            role_id = 1;
        }
        String sql = "UPDATE users SET username = ?, password = ?, roles_id = ?  WHERE username = ?";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(2, NewUsername);
            stmt.setString(4, Password);
            stmt.setInt(5, role_id);
            stmt.setString(6, Username);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void DeleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

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
}
