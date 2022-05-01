import console.ConsoleOutputer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ASCIIArt {

    protected static void greetings(ConsoleOutputer output) {
        output.printNormal(" _______             _______\n" +
                "|@|@|@|@|           |@|@|@|@|                        LABA 7\n" +
                "|@|@|@|@|   _____   |@|@|@|@|  \n" +
                "|@|@|@|@| /\\_T_T_/\\ |@|@|@|@|\n" +
                "|@|@|@|@||/\\ T T /\\||@|@|@|@|\n" +
                " ~~~~/|~||~\\/~T~\\/~||~T~~T\\~                      привет, друг\n" +
                "   /_,|_| \\(-(O)-)/ |_|__|/                         я уже вижу твое желание поломать эту лабу\n" +
                "  /~\\      \\\\8_8//    |_ |_                       но я не пущу тебя пока ты не авторизуешься\n" +
                " (O_O)  /~~[_____]~~\\   [(@)|\n" +
                "       (  |       |  )    ~\n" +
                "      [~` ]       [ '~]\n" +
                "      |~~|         |~~|\n" +
                "      |  |         |  |\n" +
                "     _<\\/>_       _<\\/>_\n" +
                "    /_====_\\     /_====_\\");

//        output.printNormal("\n" +
//                "                                ,▄▄@▓▀▀▀▀▀▀▀▀▀▀@▄▄,╓▄▄,  ╓▄╖\n" +
//                "                            ╓φÑ▀░░░░░░░░░░░░░░░░░░░▀▓▓▓▌▓▓▓▌\n" +
//                "                         ╓@▀░░░░░░░░░░░░░░░░░░░░░▓▓▓▓▓▓▓▓▓▓▓▄▄▄,\n" +
//                "                       ╓▓░░░░░░░░░░░░░░░░░░░░░░░░╙▓▓▓▓▌▒╢▓▓▓▓▓▓▓▌ ,▄▄\n" +
//                "                     ,▓░░░░░░░░░░░░░░░░░░░░░░░╫▓▓░░g▓▓█▓▓▓▓▓▓▄▓▓▓▓▓▓▓\n" +
//                "                    g▀░░░░░░░░░░░░░░░░░▄▄▄██▓░▓▓▓▌╫▓▓▓▓▓▓▓▓▓▓▓▓▀▒▓▓▓▓,\n" +
//                "                   ▐▌░░░░░░░░░░░░░░░╓█▀▀░░╟▓▓▓▓▓▓▓▓▓▓▒▒▓▓▓▒▓▓▓▓▒╢▓▓▓▓▓▌\n" +
//                "                  ▐▌░░▄▄▄▄▄▄░░░░░░░█▀░░░░░░░▀▓▓▌▒▒▓▓▓▓▓▓█▓▓▓▓▓▓▓▓▓▓▓▓\n" +
//                "                  ▓░░▀░░░░░▀██░░░░░░░▄████▀▓▓▓▓▓▓▓▓▓▓▓▀▀█▌▒▒▓▓▓▓█▓▓▓▌\n" +
//                "                 ▐▌░░░░▄█▀███▄░░░░░╙██▄███▓▓▓▀╢▓▓▓▓▓▓█▒▒▓█▒▓█▒▓▓█▌\n" +
//                "                 ▓▒░░░██▄▄███▀░░░░░░░░░░░░░░░░░▓▓▌░░▀▀█@▓█▓█▒▒▒╢▓▌\n" +
//                "                 ▓▒░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╫████▓█▓█▓▓█╣▓▌\n" +
//                "                 ▐▌░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▄▄█▓██████▓█▓╢█▌\n" +
//                "                  █░░░░░░░░░░░░░░░░░░░░░░░▄█▌░░░▐▓░░░▐███████▓▒╫█\n" +
//                "                  ▐▌░░░░░░░░░░░░░░░░░░░▄█▀`█▌░░░█▓▄░▒▒█▌░░░░░▀▀█▌\n" +
//                "                   ▐▌░░░░░▐██▄▄▄▄░▄▄██▀'  ]█░░░░███░░░██▄▄▄░▒▒▓█`\n" +
//                "                    ╙▓░░░░░░▀██▓▀▀      ,█▌░▒▒▒██▌░░░█▌░▒▒▒▒▒▒█[\n" +
//                "                      ▀▄▒▒▒▒▒▒░▀▀█▄▄,,,▄▄█▒▒▒▒▒▒▓█░░░╠█▄▒▒▒╢╢╢▓█\n" +
//                "                       ╙▀▄▒▒▒▒▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒▒▒█▄░░██▀▀▀▀▒▒▒█▌\n" +
//                "                          ▀▓▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒╢╢▓███▌░░▒▒▒▒╣╢╫█\n" +
//                "                             ▀▀▓▄▒▒▒▒▒▒▒▒▒╢╢╣╢╢╢╢╢████▀▀▀▀▀▒╢██\n" +
//                "                                 ▀▀▀▓████████▀▀▀▀▀█▄▒▒▒▒▒╢╢╢▓█\n" +
//                "                                                    `▀▀▀▀▀▀▀▀▀\n");

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

    protected static void ifExit(List<String> command, ConsoleOutputer output) {

        if (command.contains("exit")) {

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
class ASCIIArtService {

    public void draw() throws IOException {

        int width = 100;
        int height = 30;

        BufferedImage image = ImageIO.read(new File("harosh.png"));
        //BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //graphics.drawString("LABA 7", 10, 20);

        //save this image
        ImageIO.write(image, "png", new File("harosh.png"));

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {

                sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");

            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
        }

    }

}