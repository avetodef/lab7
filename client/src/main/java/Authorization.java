import console.ConsoleOutputer;
import exceptions.ExitException;
import interaction.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Authorization {

    protected static User askIfAuth(Scanner sc) {
        try {
            System.out.println("впервые тут? {y/n}");
            String answ = " ";

            while (true) {
                answ = sc.nextLine();
                switch (answ) {

                    case ("y") -> {
                        System.out.println("придумай юзернейм");
                        String username = sc.nextLine();
                        System.out.println("теперь пароль");
                        String password = sc.nextLine();
                        //TODO тут проверку вместе с БД есть ли такой юзер в БД

                        return new User(username, PasswordHandler.encode(password));
                    }
                    case ("n") -> {
                        System.out.println("введи юзернейм");
                        String newUsername = sc.nextLine();
                        System.out.println("теперь пароль");
                        String newPassword = sc.nextLine();
                        //TODO опять же проверки по типу есть ли такой юзернейм или пароль в БД

                        return new User(newUsername, PasswordHandler.encode(newPassword));
                    }
                    case "exit" -> {
                        List<String> input = new ArrayList<>();
                        input.add("exit");
                        ASCIIArt.ifExit(input, new ConsoleOutputer());
                    }
                    case "admin" -> {
                        return new User("dfgdgdfgdfgdg", PasswordHandler.encode("6426429fdfhadyf723gfksdy374kgs732jfs8ergf9w4f"));
                    }

                    default -> System.out.println("скажи пожалуйста... Y/N. Или ты хочешь уйти? Тогда пиши exit");

                }
            }
        } catch (NoSuchElementException e) {
            throw new ExitException("ewwww you are so cringe");
        }
    }

}
