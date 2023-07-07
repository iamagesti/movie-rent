package movierent.views;

import java.util.Scanner;


import movierent.entities.UserEntity;
import movierent.usecases.MovieUsecase;
import movierent.utils.ValidationUtils;

public class AdminMovieView {
      public static void ShowMovie(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);

        // TopikUsecase topikUsecase = new TopikUsecase();
        // topikUsecase.GetTopikList();

        System.out.println("===");
        System.out.println("MENU");
        System.out.println("===");
        System.out.println("[1] - Add New Movie");
        System.out.println("[2] - Delete Movie");
        System.out.println("[3] - Show Movie List");
        System.out.println("[4] - Back to Home Page");
        System.out.println("[0] - Logout");
        System.out.println();
        System.out.print("Input Menu (number) : ");
        Integer menu = ValidationUtils.GetInputMenu();

        switch (menu) {
            case 0:
                WelcomeView.ShowWelcomeView();
                break;
            case 1:
                ShowAddMovie(user);
                break;
            case 2:
                ShowDeleteMovie(user);
                break;
            case 3:
                ShowMovieOnly(user);
                break;
            case 4:
                AdminHomeView.ShowUserMain(user);
                break;
            default:
                System.out.println("Please input the correct number from menu");
                ShowMovie(user);
        }

        inputScanner.close();
    }

    private static void ShowAddMovie(UserEntity user) {
       
        String title = "";
        String genre = "";
        MovieUsecase movieUsecase = new MovieUsecase();
        System.out.println("==========================");
        System.out.println(" Add Movie");
        System.out.println("==========================");
        Scanner inputScanner = new Scanner(System.in);
        movieUsecase.GetMovieList();
        System.out.println("==========================");
        System.out.println("Please insert new movie");
        System.out.print("Title Movie: ");
        title = inputScanner.nextLine();
        System.out.print("Genre: ");
        genre = inputScanner.nextLine();
        if (movieUsecase.IsMovieTitleExist(title)) {
        System.out.println();
        }
        // Adding the new movie
        movieUsecase.AddMovie(title, genre);
    
        System.out.println();;
        AdminMovieView.ShowMovie(user);
    }
    

    private static void ShowDeleteMovie(UserEntity user){
        int movies_id =0;
        MovieUsecase movieUsecase = new MovieUsecase();
        System.out.println("==========================");
        System.out.println(" Delete Movie");
        System.out.println("==========================");
        Scanner inputScanner = new Scanner(System.in);
        movieUsecase.GetMovieList();
        System.out.println("Please enter the movie ID to delete:");
        System.out.print("Movie ID: ");
        movies_id = inputScanner.nextInt();

       if (!movieUsecase.IsRentUseThisMovie(movies_id)) {
        movieUsecase.DeleteMovie(movies_id);
        } else {
        System.out.println("This movie is currently being rented. Delete movie failed!");
        }
         System.out.println();

        AdminMovieView.ShowMovie(user);
    }
    public static void ShowMovieOnly(UserEntity user){
        MovieUsecase movieUsecase = new MovieUsecase();
        System.out.println("==========================");
        System.out.println("Movie List");
        System.out.println("==========================");
        //movieUsecase.GetMovieList();
        Integer movieCount = movieUsecase.GetMovieList();
        if (movieCount == 0) {
            System.out.println("No movies found.");
        }
        System.out.println();
        AdminMovieView.ShowMovie(user);
    }
   


    
   
 
}

