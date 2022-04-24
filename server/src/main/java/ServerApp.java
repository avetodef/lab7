import commands.ACommands;
import commands.Save;
import console.ConsoleOutputer;
import dao.RouteDAO;
import exceptions.ExitException;
import file.FileManager;
import interaction.Request;
import interaction.Response;
import interaction.Status;
import json.JsonConverter;
import utils.IdGenerator;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class ServerApp {

    Scanner sc = new Scanner(System.in);
    FileManager manager = new FileManager();
    RouteDAO dao = manager.read();
    ConsoleOutputer output = new ConsoleOutputer();

    protected void mainServerLoop() throws IOException {

        IdGenerator.reloadId(dao);
        ACommands command;
        Response serverResponse;
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

                    InputStream socketInputStream = client.getInputStream();
                    OutputStream socketOutputStream = client.getOutputStream();

                    DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

                    output.printPurple("Клиент подключился");

                    ClientHandler clientHandler = new ClientHandler(client);

                    new Thread(clientHandler).start();

//                    while (true) {
//
//                        try {
//                            output.printWhite("готов принимать запросы от клиента");
//                            String requestJson;
//                            StringBuilder builder = new StringBuilder();
//
//                            int byteRead;
//
//                            while ((byteRead = socketInputStream.read()) != -1) {
//                                if (byteRead == 0) break;
//                                builder.append((char) byteRead);
//                            }
//
//                            requestJson = builder.toString();
//
//                            Request request = JsonConverter.des(requestJson);
//
//                            command = ACommands.getCommand(request);
//
//                            serverResponse = command.execute(dao);
//                            dataOutputStream.writeUTF(JsonConverter.serResponse(serverResponse));
//
//                            Save.execute(dao);
//
//                        } catch (NullPointerException e) {
//                            errorResponse.setMsg("Введённой вами команды не существует. Попробуйте ввести другую команду." + e.getLocalizedMessage()
//                                    + e.getCause());
//                            dataOutputStream.writeUTF(JsonConverter.serResponse(errorResponse));
//
//                        } catch (NoSuchElementException e) {
//                            throw new ExitException("пока............");
//
//                        } catch (BindException e) {
//                            e.printStackTrace();
//
//                        } catch (UTFDataFormatException e) {
//                            errorResponse.setMsg("зачем прогу ломаешь, зачем добавляешь 500+ элементов ");
//                            dataOutputStream.writeUTF(JsonConverter.serResponse(errorResponse));
//                        }
//                    }
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


    private void clientUpal() {

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

}
