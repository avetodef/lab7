
import console.ConsoleOutputer;

import java.util.List;
import java.util.stream.StreamSupport;

public class ASCIIArt {

    protected static void greetings(ConsoleOutputer output) {
        //outputer.printCyan("\t\t\t\t\t▒██░░░─░▄█▀▄─░▐█▀▄──░▄█▀▄─     ▒█▀▀ \n\t\t\t\t\t▒██░░░░▐█▄▄▐█░▐█▀▀▄░▐█▄▄▐█     ▒▀▀▄ \n\t\t\t\t\t▒██▄▄█░▐█─░▐█░▐█▄▄▀░▐█─░▐█     ▒▄▄▀ ");
//        output.printCyan(" " + System.lineSeparator()+
//                          "__________ " + System.lineSeparator()+
//                         ".'----------`.                             " + System.lineSeparator() +
//                         "| .--------. |                            " + System.lineSeparator()+
//                         "| | LABA 6 | |       ___________             "+ System.lineSeparator()+
//                         "| |########| |      /___________\\            " + System.lineSeparator()+
//                ".--------| `--------' |------|    --=--  |-------------." + System.lineSeparator()+
//                "|        `----,-.-----'      |SUIR MOMENT|             |" + System.lineSeparator()+
//                "|       ______|_|_______     |___________|             |" + System.lineSeparator()+
//               " |      /  NIKA AND SOFIA \\                             |" + System.lineSeparator()+
//                "|     /    PRODUCTION     \\                            |" + System.lineSeparator()+
//                "|     ^^^^^^^^^^^^^^^^^^^^                            |" + System.lineSeparator()+
//                "+-----------------------------------------------------+" + System.lineSeparator()+
//                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");


    }

    protected static void ifExit(List<String> command, ConsoleOutputer output){

        if (command.contains("exit")){
//            output.printPurple("""
//                       _______________                        |*\\_/*|________
//                      |  ___________  |     .-.     .-.      ||_/-\\_|______  |
//                      | |            | |   .****. .****.     | |           | |
//                      | |   0   0    | |   .*****.*****.     | |   0   0   | |
//                      | |     -      | |    .*********.      | |     -     | |
//                      | |   \\___/    | |     .*******.       | |   \\___/   | |
//                      | |___CLIENT___| |      .*****.        | |___SERVER__| |
//                      |_____|\\_/|_____|        .***.         |_______________|
//                        _|__|/ \\|_|_.............*.............._|________|_
//                       / ********** \\          ПОКА             / ********** \\
//                     /  ************  \\                      /  ************  \\
//                    --------------------                    --------------------""");

            Exit.execute();
        }
    }
}
