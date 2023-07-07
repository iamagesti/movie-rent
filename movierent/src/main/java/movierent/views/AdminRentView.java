package movierent.views;

import java.util.Date;
import java.util.Scanner;

import movierent.entities.RentEntity;
import movierent.entities.UserEntity;
import movierent.usecases.MovieUsecase;
import movierent.usecases.RentUsecase;
import movierent.utils.ValidationUtils;

public class AdminRentView {
    public static void ShowRent(UserEntity user) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("===");
        System.out.println("MENU");
        System.out.println("===");
        System.out.println("[1] - List Rent");
        System.out.println("[2] - Add Rent");
        System.out.println("[3] - Return Rent");
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
                ListRent(user);
                break;
            case 2:
                ShowAddRent(user);
                break;
            case 3:
                ShowReturnRent(user);
                break;
            case 4:
                AdminHomeView.ShowUserMain(user);
                break;
            default:
                System.out.println("Please input the correct number from menu");
                ShowRent(user);
        }

        inputScanner.close();
    } 
    public static void ListRent(UserEntity user) {
    RentUsecase rentUsecase = new RentUsecase();

    System.out.println("==========================");
    System.out.println("Rent List");
    System.out.println("==========================");

    RentEntity[] rentList = rentUsecase.GetRentList();
    if (rentList.length == 0) {
        System.out.println("No active rents found.");
    } else {
        printRentList(rentList);
    }

    System.out.println();
    ShowRent(user);
}

    private static void printRentList(RentEntity[] rentList) {
    System.out.println("rent_id\tmovies_id\ttitle\t\trent_date\trenter\tusername");
    System.out.println("----------------------------------------------------------------------------");
    for (RentEntity rent : rentList) {
        System.out.printf("%-8d%-12d%-20s%-15s%-8d%s%n", rent.getRentID(), rent.getMoviesID(), rent.getMoviesTitle(),
                rent.getRentDate(), rent.getRenterID(), rent.getUsername());
    }
}

     public static void ShowAddRent(UserEntity user) {
        boolean movieTitleExist = false;
        boolean movieExist = false;
        String title ="";
        MovieUsecase movieUsecase = new MovieUsecase();
        RentUsecase rentUsecase = new RentUsecase();
        //String Username = user.getUsername();
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("==========================");
        System.out.println("Add Rent");
        System.out.println("==========================");
        movieUsecase.GetMovieList();
        while(!movieTitleExist){
            System.out.print("Enter movie title: ");
            title = inputScanner.nextLine();
            movieTitleExist = movieUsecase.IsMovieTitleExist(title);
            //String title = inputScanner.nextLine();
        }
        
        System.out.print("Enter rent date (yyyy-mm-dd): ");
        Date rent_date = ValidationUtils.GetValidDateInput();

        System.out.print("Enter username: ");
        String username = inputScanner.nextLine();
        
        rentUsecase.AddRent(title,rent_date, username);
        System.out.println();
        ListRent(user);
        System.out.println();
        AdminRentView.ShowRent(user);
    }
    
    
    public static void ShowReturnRent(UserEntity user) {
        RentUsecase rentUsecase = new RentUsecase();
    
        System.out.println("==========================");
        System.out.println("Return Rent");
        System.out.println("==========================");
        RentEntity[] rentList = rentUsecase.GetRentList();
        printRentList(rentList); // Tampilkan tabel list rent
        Scanner inputScanner = new Scanner(System.in);
    
        System.out.print("Enter rent ID: ");
        Integer rent_id = ValidationUtils.GetInputMenu();
    
        System.out.print("Enter return date (yyyy-MM-dd): ");
        Date return_date = ValidationUtils.GetValidDateInput();
    
        boolean rentExists = false;
        for (RentEntity rent : rentList) {
            if (rent.getRentID().equals(rent_id)) {
                rentExists = true;
                break;
            }
        }
    
        if (rentExists) {
            rentUsecase.ReturnRent(rent_id, return_date);
            System.out.println();
        } else {
            System.out.println("Rent not found, Return Movie Failed");
        }
    
        System.out.println();
    
        AdminRentView.ShowRent(user);
    }
    
 
}
