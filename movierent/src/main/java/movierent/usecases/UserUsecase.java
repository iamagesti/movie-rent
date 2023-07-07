package movierent.usecases;

import com.zaxxer.hikari.HikariDataSource;

import movierent.entities.UserEntity;
import movierent.models.UserModel;
import movierent.utils.DBConnectionUtil;

public class UserUsecase {
     private HikariDataSource dataSource;
    private UserModel userModel;

    public UserUsecase() {
        dataSource = DBConnectionUtil.getDataSource();
        userModel = new UserModel(dataSource);
    }

    public Integer GetUserList() {
        
        UserEntity[] userList = userModel.findAllUser();
        return userList.length;
    }

    public void ChangePasswordUser(String newuserid, String newpass) {
        UserEntity userData = new UserEntity();
        userData.setUsername(newuserid);
        userData.setPassword(newpass);
        userModel.ChangePassword(userData);
        System.out.println("Change Password Succeed!");
    }

    public void EditUser(String NewUsername, String Password, String Username, String Role) {
        if (IsUserExist(Username)) {
            if (!IsUserExist(NewUsername) || Username.equals(NewUsername)) {
                userModel.EditUser(NewUsername, Password, Username, Role);
                System.out.println("Edit User Succeed");
            } else {
                System.out.println("New Username is already exist, edit user failed");
            }
        } else {
            System.out.println("Username is not exist, edit user failed");
        }
    }

    public void DeleteUser(String username) {
        if (IsUserExist(username)) {
            userModel.DeleteUser(username);
            System.out.println("Delete User Succeed");
        } else {
            System.out.println("Username is not exist, delete user failed");
        }
    }

    public Boolean IsUserExist(String username) {
        if (userModel.CheckUserExist(username)) {
            return true;
        } else {
            return false;
        }
    }
}
