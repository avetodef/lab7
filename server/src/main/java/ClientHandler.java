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

import java.io.*;
import java.net.BindException;
import java.net.Socket;
import java.util.NoSuchElementException;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    ConsoleOutputer output = new ConsoleOutputer();
    ACommands command;
    Response serverResponse;
    FileManager manager = new FileManager();
    RouteDAO dao = manager.read();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        InputStream socketInputStream = null;
        OutputStream socketOutputStream = null;
        DataOutputStream dataOutputStream = null;

        Response errorResponse = new Response();
        errorResponse.setStatus(Status.SERVER_ERROR);

        try{
            socketInputStream = clientSocket.getInputStream();
            socketOutputStream = clientSocket.getOutputStream();
            dataOutputStream = new DataOutputStream(socketOutputStream);

            while (true) {

                try {
                    output.printWhite("готов принимать запросы от клиента");
                    String requestJson;
                    StringBuilder builder = new StringBuilder();

                    int byteRead;

                    while ((byteRead = socketInputStream.read()) != -1) {
                        if (byteRead == 0) break;
                        builder.append((char) byteRead);
                    }

                    requestJson = builder.toString();

                    Request request = JsonConverter.des(requestJson);

                    command = ACommands.getCommand(request);

                    serverResponse = command.execute(dao);
                    dataOutputStream.writeUTF(JsonConverter.serResponse(serverResponse));

                    Save.execute(dao);

                } catch (NullPointerException e) {
                    errorResponse.setMsg("Введённой вами команды не существует. Попробуйте ввести другую команду." + e.getLocalizedMessage()
                            + e.getCause());
                    dataOutputStream.writeUTF(JsonConverter.serResponse(errorResponse));

                } catch (NoSuchElementException e) {
                    throw new ExitException("пока............");

                } catch (BindException e) {
                    e.printStackTrace();

                } catch (UTFDataFormatException e) {
                    errorResponse.setMsg("зачем прогу ломаешь, зачем добавляешь 500+ элементов ");
                    dataOutputStream.writeUTF(JsonConverter.serResponse(errorResponse));
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
