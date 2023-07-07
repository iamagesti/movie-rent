package movierent.usecases;

import com.zaxxer.hikari.HikariDataSource;

import movierent.entities.UserEntity;
import movierent.models.LoginModel;
import movierent.utils.DBConnectionUtil;

public class LoginUsecase {
     private HikariDataSource dataSource;
    private LoginModel loginModel;

    public LoginUsecase() {
        dataSource = DBConnectionUtil.getDataSource();
        loginModel = new LoginModel(dataSource);
    }

    public String LoginValidate(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return "userid or password cannot be empty!";
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);

        if (!loginModel.CheckUserExist(user.getUsername())) {
            return "username is not exist, please try again.";
        }

        if (!loginModel.UserPasswordValid(user)) {
            return "username and password is not valid, please try again";
        }
        else {
            Integer role_id = loginModel.GetRoleId(username);
            user.setRoleid(role_id);
            return String.valueOf(role_id);
        }
    }

    public void loginPassed (String username, UserEntity userData){
        System.out.println("Login Success!");
        userData.setUsername(username);
    }


}
