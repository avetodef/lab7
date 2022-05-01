import console.ConsoleOutputer;
import exceptions.ExitException;
import interaction.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Authorization {

    private final ConsoleOutputer o = new ConsoleOutputer();
    public static boolean isAuth = false;
    private int id;
    private String newUsername;
    private String newPassword;

    protected User askIfAuth(Scanner sc) {

        try {
            System.out.println("впервые тут? {Y/N}");
            String answ;

            while (true) {
                answ = sc.nextLine();
                switch (answ) {

                    case ("Y"): {
                        o.printNormal("придумай юзернейм");
                        String username = sc.nextLine();
                        o.printNormal("теперь пароль");
                        String password = sc.nextLine();
                        id = 398; //TODO генерацию айди
                        o.printNormal("тебе назначен id: " + id);
                        //TODO тут проверку вместе с БД есть ли такой юзер в БД
                        isAuth = true;
                        o.printPurple("Для того чтобы начать введите команду. Чтобы увидеть список доступных команд введите help");
//TODO надо настроить генерацию айди через sql как-то
                        return new User(username, PasswordHandler.encode(password), id);
                    }
                    case ("N"): {
                        o.printNormal("помнишь свой id? {y/n}");
                        String s = sc.nextLine();

                        switch (s) {

                            case "y": {
                                o.printNormal("введи id");
                                while (true) {
                                    try {
                                        id = Integer.parseInt(sc.nextLine()); //TODO настроить проверку айдишников пользователей
                                        break;
                                    } catch (RuntimeException e) {
                                        o.printRed("и че это за тип данных такой");

                                    }
                                }
                                newUsername = "user_default"; //TODO тут наверное поиск по таблице юзеров надо сделать
                                newPassword = "user_default";
                                break;
                            }
                            case "n": {
                                o.printNormal("введи юзернейм");
                                newUsername = sc.nextLine();
                                o.printNormal("теперь пароль");
                                newPassword = sc.nextLine();
                                id = 421; //TODO присвоение пароля через скл
                                break;
                            }
                            default:
                                o.printRed("и че это значит");
                        }

                        //TODO опять же проверки по типу есть ли такой юзернейм или пароль в БД
                        isAuth = true;
                        o.printPurple("Для того чтобы начать введите команду. Чтобы увидеть список доступных команд введите help");

                        return new User(newUsername, PasswordHandler.encode(newPassword), id);
                    }
                    case "exit": {
                        List<String> input = new ArrayList<>();
                        input.add("exit");
                        ASCIIArt.ifExit(input, new ConsoleOutputer());
                    }
                    case "admin": {
                        isAuth = true;
                        o.printPurple("Для того чтобы начать введите команду. Чтобы увидеть список доступных команд введите help");
                        return new User("admin", PasswordHandler.encode("dfmjosdfo8107142827sidhfsodffsd47918741"), 0);
                    }
                    default: {
                        isAuth = false;
                        o.printRed("не авторизированным пользователям доступны только команды Y/N/exit");
                    }

                }
            }
        } catch (NoSuchElementException e) {
            throw new ExitException("ewwww you are so cringe");
        }
    }

    private int userIdGenerator() {
        //TODO написать по нормальному эту генерацию айдишников
        //может быть ее надо написать в датабейс манаждере а не тут и потом вызывать его как то

//        try {
//            int id = -1;
//            PreparedStatement prep = conn.prepareStatement("INSERT INTO TABLE (USER_ID) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
//            prep.setString(1, "user_id");
//            prep.executeUpdate();
//            ResultSet rs = prep.getGeneratedKeys();
//            if (rs.next()) {
//                id = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            System.out.println("problems occured while generating a new id: " + e.getMessage());
//        }
        return id;
    }

}
