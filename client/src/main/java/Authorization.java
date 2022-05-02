import ASCII.ASCIIArt;
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
                        while(true) {
                            o.printNormal("придумай юзернейм");
                            String username = sc.nextLine();

                            if (такой логин есть в базе данных){
                                o.printNormal("этот юзернейм занят");
                            }
                            else
                            {
                                o.printNormal("теперь пароль");
                                String password = PasswordHandler.encode(sc.nextLine());

                                id = 398; //TODO генерацию айди через базу данных
                                o.printNormal("тебе назначен id: " + id);
                                //TODO тут проверку вместе с БД есть ли такой юзер в БД
                                isAuth = true;


                        o.printPurple("Для того чтобы начать введите команду. Чтобы увидеть список доступных команд введите help");
//TODO надо настроить генерацию айди через sql как-то
                        return new User(username, password, id);
                            }
                        break;
                        }
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
                                        if (такой айди есть в базе данных){
                                            break;
                                        }
                                        else{
                                            o.printNormal("такого id нет в базе данных");
                                            continue;
                                        }
                                        break;
                                    } catch (RuntimeException e) {
                                        o.printRed("и че это за тип данных такой");

                                    }
                                }
                                newUsername = "user_default"; //TODO здесь нужно восстановить юзера по айди
                                newPassword = "user_default";
                                break;
                            }
                            case "n": {
                                while(true) {

                                    o.printNormal("введи юзернейм");
                                    newUsername = sc.nextLine();
                                    if (нет такого юзернейма){
                                        o.printNormal("такого юзернейма нет");
                                        continue;
                                    }
                                    else{
                                        o.printNormal("теперь пароль");
                                        newPassword = PasswordHandler.encode(sc.nextLine());
                                        String passwordFromDataBase = PasswordHandler.decode(пароль этого юзера из базы данных);
                                        if( newPassword != passwordFromDataBase){
                                            o.printNormal("введен неправильный пароль");
                                            continue;
                                        }
                                        else {
                                            id = пароль юзера которого ввели наверху; //TODO восстановление пароля по логину и паролю
                                            break;
                                        }

                                    }
                                    break;
                                }
                                break;
                            }
                            default:
                                o.printRed("и че это значит");
                        }


                        isAuth = true;
                        o.printPurple("Для того чтобы начать введите команду. Чтобы увидеть список доступных команд введите help");

                        return new User(newUsername, newPassword, id);
                    }
                    case "exit": {
                        Exit.execute();
                    }
                    default: {
                        isAuth = false;
                        o.printRed("не авторизированным пользователям доступны только команды Y/N/exit");
                    }

                }
            }
        } catch (NoSuchElementException e) {
            throw new ExitException("иу");
        }
    }

}
