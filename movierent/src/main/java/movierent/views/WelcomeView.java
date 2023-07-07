package movierent.views;

import java.util.Scanner;

import movierent.utils.ValidationUtils;

public class WelcomeView {
    public static void main(String[] args) {
        ShowWelcomeView();
    }

    public static void ShowWelcomeView(){
        Scanner inputScanner = new Scanner (System.in);
        System.out.println("=======================");
        System.out.println("Welcome to Movie Rent App");
        System.out.println("=======================");
        System.out.println();
        System.out.println("[1] - Login Page");
        System.out.println("[2] - Register Account");
        System.out.println("[0] - Exit");
        System.out.println();
        System.out.println("Input Menu (number) : ");
        Integer menu = ValidationUtils.GetInputMenu();
        switch (menu) {
            case 1:
                System.out.println("Go to Login Page");
                LoginView.ShowLoginPage();
                break;
            case 2:
                System.out.println("Go to Register Page");
                RegistView.ShowRegisterPage();
                // ShowWelcomeView();
                break;
            case 0:
                System.out.println("Application Closed!");
                System.exit(0);
                break;
            default:
                System.out.println("Please input the correct number from menu");
                ShowWelcomeView();
        }
        inputScanner.close();
    }
}
