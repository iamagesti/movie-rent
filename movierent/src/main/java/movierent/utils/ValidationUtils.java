package movierent.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.ParseException;

import movierent.entities.UserEntity;

public class ValidationUtils {
     public static void LoginValidate(UserEntity user) throws Throwable {
        if (user.getUsername().isEmpty()) {
            throw new Throwable("username is blank");
        } else if (user.getPassword().isEmpty()) {
            throw new Throwable("getPassword() is blank");
        }
    }

    public static void RegisterValidate(UserEntity user) throws Throwable {
        if (user.getUsername().isEmpty()) {
            throw new Throwable("username is blank");
        } else if (user.getPassword().isEmpty()) {
            throw new Throwable("password is blank");
        } 
    }

    public static Integer GetInputMenu() {
        Scanner Scanner = new Scanner(System.in);
        Integer menu = -1;
        try {
            menu = Scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Your input is not number");
        }
        // Scanner.close();
        return menu;
    }

     public static Date GetValidDateInput() {
        Scanner scanner = new Scanner(System.in);
        Date date = null;
        boolean isValid = false;

        while (!isValid) {
            String input = scanner.nextLine();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = new Date(dateFormat.parse(input).getTime());
                isValid = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }

        return date;
    }

}
