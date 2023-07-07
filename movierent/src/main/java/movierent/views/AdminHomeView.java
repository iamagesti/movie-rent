package movierent.views;

import java.util.Scanner;


import movierent.entities.UserEntity;
import movierent.utils.ValidationUtils;

public class AdminHomeView {
    public static void ShowUserMain(UserEntity user){
         Scanner inputScanner = new Scanner(System.in);
        System.out.println("===");
        System.out.println("MENU");
        System.out.println("===");
        System.out.println("[1] - Manage Movie");
        System.out.println("[2] - Manage Rent");
        System.out.println("[3] - Manage User");
        System.out.println("[0] - Logout");
        System.out.println();
        System.out.print("Input Menu (number) : ");
        Integer menu = ValidationUtils.GetInputMenu();
        switch (menu) {
            case 1:
                AdminMovieView.ShowMovie(user);
                //add opik
                break;
            case 2:
                AdminRentView.ShowRent(user);
                break;
            case 3:
                AdminUserView.ShowUser(user);
                break;
            case 0:
                WelcomeView.ShowWelcomeView();
                break;
            default:
                System.out.println("Please input the correct number from menu");
                ShowUserMain(user);
        }
        ShowUserMain(user);
        inputScanner.close();
    }
    
}
