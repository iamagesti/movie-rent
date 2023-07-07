package movierent.usecases;

import com.zaxxer.hikari.HikariDataSource;

import movierent.entities.UserEntity;
import movierent.models.RegisterModel;
import movierent.utils.DBConnectionUtil;
import movierent.utils.ValidationUtils;

public class RegisterUsecase {
    private HikariDataSource dataSource;
    private RegisterModel RegisterModel;

    public RegisterUsecase() {
        dataSource = DBConnectionUtil.getDataSource();
        RegisterModel = new RegisterModel(dataSource);
    }

    public void RegisterUser(String username,String pass, String Role) {
        Integer role_id = -1;
        if (Role.toLowerCase().equals("admin")){
            role_id = 0;
        } else if (Role.toLowerCase().equals("user")) {
            role_id = 1;
        }
        if (!IsUserExist(username)) {
            UserEntity userData = new UserEntity();
            userData.setUsername(username);
            userData.setPassword(pass);
            userData.setRoleid(role_id);
            try {
                ValidationUtils.RegisterValidate(userData);
                RegisterModel.CreateUser(userData);
                System.out.println("Create user succeed");
                // LoginView.ShowLoginPage();
            } catch (Throwable ex) {
                System.out.println("Register invalid - " + ex.getMessage());
            }
        } else {
            System.out.println("Username already exist, create user failed");
        }
    }

    public Boolean IsUserExist(String username) {
        UserEntity user = new UserEntity();
        user.setUsername(username);

        if (RegisterModel.CheckUserExist(user.getUsername())) {
            // System.out.println("Username already exist, please insert another username");
            return true;
        } else {
            return false;
        }
    } 
}
