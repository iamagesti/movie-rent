package movierent.views;

import java.util.Scanner;

import movierent.entities.UserEntity;
import movierent.usecases.RegisterUsecase;
import movierent.usecases.UserUsecase;
import movierent.utils.ValidationUtils;

public class AdminUserView {
  public static void ShowUser(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("===");
        System.out.println("MENU");
        System.out.println("===");
        System.out.println("[1] - Add New User");
        System.out.println("[2] - Delete User");
        System.out.println("[3] - Show User");
        System.out.println("[4] - Change My Password");
        System.out.println("[5] - Back to Home Page");
        System.out.println("[0] - Logout");
        System.out.println();
        System.out.print("Input Menu (number) : ");
        Integer menu = ValidationUtils.GetInputMenu();

        switch (menu) {
            case 0:
                WelcomeView.ShowWelcomeView();
                break;
            case 1:
                ShowAddUser(user);
                break;
            case 2:
                ShowDeleteUser(user);
                break;
            case 3:
                ShowUserOnly(user);
                break;
            case 4:
                ShowChangePassword(user);
                break;
            case 5:
                AdminHomeView.ShowUserMain(user);
                break;
            default:
                System.out.println("Please input the correct number from menu");
                ShowUser(user);
        }

        inputScanner.close();
    }

     private static void ShowAddUser(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);
        UserUsecase userUsecase = new UserUsecase();
        System.out.println("=========================");
        System.out.println("Add New User");
        System.out.println("=========================");
        System.out.print("Username : ");
        String username = inputScanner.nextLine();
        if (!userUsecase.IsUserExist(username)) {
            System.out.print("PASSWORD : ");
            String pass = inputScanner.nextLine();
            System.out.print("Role (admin/user) : ");
            String role = inputScanner.nextLine();

            RegisterUsecase registerUsecase = new RegisterUsecase();
            registerUsecase.RegisterUser(username,pass, role);
        } else {
            System.out.println("Username already exist");
        }
        ShowUser(user);
        inputScanner.close();
    }
    
    private static void ShowDeleteUser(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);
        UserUsecase userUsecase = new UserUsecase();
        if (userUsecase.GetUserList() > 0) {
            System.out.println("Please insert username to delete");
            System.out.print("Username: ");
            String iUserName = inputScanner.nextLine();
            userUsecase.DeleteUser(iUserName);
        }
        ShowUser(user);
        inputScanner.close();
    }
    
    public static void ShowUserOnly(UserEntity user) {
        UserUsecase userUsecase = new UserUsecase();
        userUsecase.GetUserList();
        ShowUser(user);
    }
    private static void ShowChangePassword(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);
        String sUserid = user.getUsername();
        System.out.println("=======================");
        System.out.println("password: ");
        String sPassword = inputScanner.nextLine();

        UserUsecase userUsecase = new UserUsecase();
        userUsecase.ChangePasswordUser(sUserid, sPassword);

        ShowUser(user);
        inputScanner.close();
    }
}
