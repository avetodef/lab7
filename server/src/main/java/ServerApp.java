import console.ConsoleOutputer;
import dao.RouteDAO;
import file.FileManager;
import interaction.Response;
import interaction.Status;
import utils.IdGenerator;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class ServerApp {

    static Scanner sc = new Scanner(System.in);
    FileManager manager = new FileManager();
    RouteDAO dao = manager.read();
    ConsoleOutputer output = new ConsoleOutputer();

    protected void mainServerLoop() throws IOException {

        IdGenerator.reloadId(dao);
        Response errorResponse = new Response();
        errorResponse.setStatus(Status.SERVER_ERROR);

        try {

            int port = 6666;
            output.printPurple("Ожидаю подключение клиента");

            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);

            while (true) {

                try {

                    Socket client = serverSocket.accept();
                    output.printPurple("Клиент подключился ");
                    ClientHandler clientHandler = new ClientHandler(client);
                    new Thread(clientHandler).start();

                } catch (SocketException e) {
                    System.err.println("клиент упал. подожди немного. закончить работу сервера? yes или no?");
                    try {
                        clientUpal();
                    } catch (NoSuchElementException exception) {
                        System.out.println("кнтрл д момент...");
                    }
                }

            }

        } catch (IllegalArgumentException e) {
            System.err.println("ну и зачем менять номер порта");
            System.err.println("исправляй а потом запускай");
        } catch (IOException e) {
            System.out.println("IO troubles " + e.getMessage());
        }
    }


    protected static void clientUpal() {
        try {
            String answer;
            while (!(answer = sc.nextLine()).equals("no")) {
                switch (answer) {
                    case "":
                        break;
                    case "yes":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("скажи пожалуйста.... yes или no");
                }
            }
            System.err.println("ну подожди еще значит");
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(e.getClass() + " " + e.getMessage());
        }

    }

}
