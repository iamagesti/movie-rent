package movierent.views;

import java.util.Scanner;

import movierent.usecases.RegisterUsecase;

public class RegistView {
    public static void ShowRegisterPage(){
        Scanner inputScanner = new Scanner(System.in);
        RegisterUsecase RegisterUsecase = new RegisterUsecase();

       System.out.println("=========================");
        System.out.println("Register Page");
        System.out.println("=========================");
        String username = "";
        System.out.print("Username : ");
        username = inputScanner.nextLine();
        if (!RegisterUsecase.IsUserExist(username)) {

            System.out.print("PASSWORD : ");
            String pass = inputScanner.nextLine();
            RegisterUsecase registerUsecase = new RegisterUsecase();
            registerUsecase.RegisterUser(username, pass, "user");
        } else {
            System.out.println("username already exists");
        }
        WelcomeView.ShowWelcomeView();
        inputScanner.close(); 
    }
}
