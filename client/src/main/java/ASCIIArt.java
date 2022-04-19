
import console.ConsoleOutputer;

import java.util.List;

public class ASCIIArt {

    protected static void greetings(ConsoleOutputer output) {
        //outputer.printCyan("\t\t\t\t\t▒██░░░─░▄█▀▄─░▐█▀▄──░▄█▀▄─     ▒█▀▀ \n\t\t\t\t\t▒██░░░░▐█▄▄▐█░▐█▀▀▄░▐█▄▄▐█     ▒▀▀▄ \n\t\t\t\t\t▒██▄▄█░▐█─░▐█░▐█▄▄▀░▐█─░▐█     ▒▄▄▀ ");
        output.printCyan("""
                          __________                                \s
                         .'----------`.                             \s
                         | .--------. |                            \s
                         | | LABA 6 | |       ___________             \s
                         | |########| |      /___________\\            \s
                .--------| `--------' |------|    --=--  |-------------.
                |        `----,-.-----'      |SUIR MOMENT|             |\s
                |       ______|_|_______     |___________|             |\s
                |      /  NIKA AND SOFIA \\                             |\s
                |     /    PRODUCTION     \\                            |\s
                |     ^^^^^^^^^^^^^^^^^^^^                            |\s
                +-----------------------------------------------------+
                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\s""");

        System.out.println("Для того чтобы начать введите команду. Чтобы увидеть список доступных команд введите help");
    }

    protected static void ifExit(List<String> command, ConsoleOutputer output){

        if (command.contains("exit")){
            output.printPurple("""
                       _______________                        |*\\_/*|________
                      |  ___________  |     .-.     .-.      ||_/-\\_|______  |
                      | |            | |   .****. .****.     | |           | |
                      | |   0   0    | |   .*****.*****.     | |   0   0   | |
                      | |     -      | |    .*********.      | |     -     | |
                      | |   \\___/    | |     .*******.       | |   \\___/   | |
                      | |___CLIENT___| |      .*****.        | |___SERVER__| |
                      |_____|\\_/|_____|        .***.         |_______________|
                        _|__|/ \\|_|_.............*.............._|________|_
                       / ********** \\          ПОКА             / ********** \\
                     /  ************  \\                      /  ************  \\
                    --------------------                    --------------------""");

            Exit.execute();
        }
    }
}
