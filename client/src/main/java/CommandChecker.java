import commands.ACommands;
import commands.CommandSaver;
import commands.ExecuteReader;
import commands.VideoRzhaka;
import console.ConsoleOutputer;
import dao.RouteDAO;
import exceptions.EmptyInputException;
import interaction.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandChecker extends ACommands {
    ConsoleOutputer output = new ConsoleOutputer();
    protected boolean ifVideoRzhaka(List<String> inp){
        if (inp.contains("video_rzhaka")){
            new Thread(new VideoRzhaka()).start();
            return true;
        }
        return false;
    }
    public boolean ifExecuteScript(List<String> inp) {
        boolean flag = false;

        String nameOfScript = inp.get(1);

        if (ExecuteReader.checkNameOfFileInList(nameOfScript)) {

            ExecuteReader.listOfNamesOfScripts.add(nameOfScript);

            try {
                List<String> listOfCommands = Files.readAllLines(Paths.get(nameOfScript + ".txt").toAbsolutePath());

                for (String lineOfFile : listOfCommands
                ) {

                    String command = lineOfFile.trim();

                    if (command.isEmpty()) {
                        throw new EmptyInputException();
                    }
                    List<String> args = new ArrayList<>(Arrays.asList(command.split(" ")));

                    try {

                        if (CommandSaver.checkCommand(args))
                            return true;
                        else {
                            output.printPurple("в скрипте параша написана, переделывай");
                            flag = false;
                        }
                    } catch (RuntimeException e) {
                        output.printPurple("в скрипте параша написана, переделывай"
                        );
                        flag = false;
                    }
                }
            } catch (NoSuchFileException e) {
                output.printBlue("нет такого файла");
                flag = false;


            } catch (IOException e) {
                output.printRed("что то пошло не так...");
                e.printStackTrace();
                flag = false;
            }
            ExecuteReader.listOfNamesOfScripts.clear();
        } else {
            output.printPurple("рекурсия... интересно кто бы мог решить сделать нам рекурсию....");
            flag = false;
        }
        return flag;
    }

    @Override
    public Response execute(RouteDAO routeDAO) {
        return null;
    }
}
