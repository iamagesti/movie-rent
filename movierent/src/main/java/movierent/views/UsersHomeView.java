package movierent.views;

import java.util.Scanner;


import movierent.entities.UserEntity;
import movierent.usecases.MovieUsecase;
import movierent.usecases.UserUsecase;
import movierent.utils.ValidationUtils;

public class UsersHomeView {
    public static void ShowUserMain(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("===");
        System.out.println("MENU");
        System.out.println("===");
        System.out.println("[1] - Movie List");
        System.out.println("[2] - Change Password");
        System.out.println("[0] - Logout");
        System.out.println();
        System.out.println("Input Menu (number) : ");
        Integer menu = ValidationUtils.GetInputMenu();
        switch (menu) {
            case 1:
                ShowMovie(user);
                break;
            case 2:
                ShowChangePassword(user);
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

    public static void ShowMovie(UserEntity user){
        MovieUsecase movieUsecase = new MovieUsecase();
        movieUsecase.GetMovieList();
        ShowUserMain(user);
    }

    private static void ShowChangePassword(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);
        String sUserid = user.getUsername();
        System.out.println("=======================");
        System.out.println("password: ");
        String sPassword = inputScanner.nextLine();

        UserUsecase userUsecase = new UserUsecase();
        userUsecase.ChangePasswordUser(sUserid, sPassword);

        ShowUserMain(user);
        inputScanner.close();
    }
}
