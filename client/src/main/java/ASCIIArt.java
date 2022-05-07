import console.ConsoleOutputer;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ASCIIArt {

    public static void greetings(ConsoleOutputer output) {
        ASCIIArtService.printASCII("LABA 7", "normal", "%");
        output.printWhite("______________________________________________________________________________________________________________________________________________________" + System.lineSeparator());
        output.printNormal(" _______               _______\n" +
                "|@|@|@|@|             |@|@|@|@|\n" +
                "|@|@|@|@|   _______   |@|@|@|@|  \n" +
                "|@|@|@|@| / \\_T_T_/ \\ |@|@|@|@|\n" +
                "|@|@|@|@||/ \\ T T / \\||@|@|@|@|\n" +
                " ~~~~/|~||~ \\/~T~\\/ ~||~T~~T\\~                      привет, друг\n" +
                "   /_,|_|  \\(-(O)-)/ |_|__|/                        я уже вижу твое желание поломать эту лабу\n" +
                "  /~\\      \\\\8_8//    |_ |_                         но я не пущу тебя пока ты не авторизуешься\n" +
                " (O_O)   /~~[_____]~~\\   [(@)|\n" +
                "        (  |       |  )    ~\n" +
                "       [~` ]       [ '~]\n" +
                "       |~~|         |~~|\n" +
                "       |  |         |  |\n" +
                "      _<\\/>_       _<\\/>_\n" +
                "     /_====_\\     /_====_\\");
        output.printWhite("______________________________________________________________________________________________________________________________________________________" + System.lineSeparator());
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
}

class ASCIIArtService {
    private static final ConsoleOutputer o = new ConsoleOutputer();

    public static void printASCII(String msg, String colour, String character){
        try{

            int width = 150;
            int height = 30;

            //BufferedImage image = ImageIO.read(new File("/Users/mkyong/Desktop/logo.jpg"));
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setFont(new Font("Lobster", Font.BOLD, 24));

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.drawString(msg, 10, 20);

            //save this image
            //ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

            for (int y = 0; y < height; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < width; x++) {

                    sb.append(image.getRGB(x, y) == -16777216 ? " " : character);

                }

                if (sb.toString().trim().isEmpty()) {
                    continue;
                }
                switch (colour){
                    case("normal"):{
                        o.printNormal(sb.toString());
                        break;
                    }
                    case("red"):{
                        o.printRed(sb.toString());
                        break;
                    }
                    case("purple"):{
                        o.printPurple(sb.toString());
                        break;
                    }
                    case("cyan"):{
                        o.printCyan(sb.toString());
                        break;
                    }
                }
            }

        }
        catch (RuntimeException e){
            System.out.println("IO");
        }
    }
}