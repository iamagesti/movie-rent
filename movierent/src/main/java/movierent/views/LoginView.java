package movierent.views;

import java.util.Scanner;

import movierent.entities.UserEntity;
import movierent.usecases.LoginUsecase;
import movierent.utils.ValidationUtils;

public class LoginView {
    public static void ShowLoginPage() {
        Scanner inputScanner = new Scanner(System.in);
        UserEntity userData = new UserEntity();

        System.out.println("=========================");
        System.out.println("Login Page");
        System.out.println("=========================");

        System.out.print("USERNAME : ");
        String username = inputScanner.nextLine();
        System.out.print("PASSWORD : ");
        String pass = inputScanner.nextLine();

        LoginUsecase loginUsecase = new LoginUsecase();
        String sValidate = loginUsecase.LoginValidate(username, pass);

        if (sValidate.equals("0")) {
            loginUsecase.loginPassed(username, userData);
            AdminHomeView.ShowUserMain(userData);
        } 
        else if (sValidate.equals("1")) {
            loginUsecase.loginPassed(username, userData);
            UsersHomeView.ShowUserMain(userData);
        }
        
        else {
            System.out.println();
            System.out.println("LOGIN FAILED - " + sValidate);
        }
        

        System.out.println("===");
        System.out.println("MENU");
        System.out.println("===");
        System.out.println("[1] - Login Page");
        System.out.println("[0] - Back to Welcome Page");
        System.out.println();
        System.out.print("Input Menu (number) : ");
        Integer menu = ValidationUtils.GetInputMenu();

        switch (menu) {
            case 0:
                WelcomeView.ShowWelcomeView();
                break;
            case 1:
                ShowLoginPage();
                break;
            default:
                System.out.println("Please input the correct number from menu");
                ShowLoginPage();
        }

        inputScanner.close();
    }
}
