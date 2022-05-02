package server;

import commands.Save;
import console.ConsoleOutputer;
import dao.RouteDAO;
import db.DataBaseDAO;
import db.DataBaseHandler;
import db.DataBaseUsersDolboeb;
import interaction.Request;
import interaction.Response;
import interaction.Status;
import interaction.User;
import json.JsonConverter;
import utils.IdGenerator;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ConsoleOutputer output = new ConsoleOutputer();
    //TODO переписать на скл
    private DataBaseDAO dao = new DataBaseDAO(new DataBaseHandler(), new DataBaseUsersDolboeb());

    private final ForkJoinPool forkJoinPool = new ForkJoinPool(3);
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private final Lock locker = new ReentrantLock();

    public Request request;
    public User user;
    private Save save;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        IdGenerator.reloadId(dao);

        InputStream socketInputStream;
        OutputStream socketOutputStream;
        DataOutputStream dataOutputStream;

        Response errorResponse = new Response(null, Status.SERVER_ERROR);

        try {
            socketInputStream = clientSocket.getInputStream();
            socketOutputStream = clientSocket.getOutputStream();
            dataOutputStream = new DataOutputStream(socketOutputStream);

            while (true) {

                try {

                    output.printWhite("готов принимать запросы от клиента");

                    locker.lock();
                    String clientMessage = this.fixedThreadPool.submit(new RequestReader(socketInputStream)).get();
                    locker.unlock();

//TODO вот здесь получен юзер
                    request = JsonConverter.des(clientMessage);
                    user = request.getUser();

                    locker.lock();
                    Response serverResponse = this.forkJoinPool.invoke(new RequestProcessor(clientMessage, dao));
                    locker.unlock();

                    locker.lock();
                    this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, serverResponse));
                    locker.unlock();
                    //TODO выкидывает нуллпоинтер, наверное потому что у меня нет бд
                    //save.execute(dao);

                } catch (NullPointerException e) {
                    errorResponse.setMsg("Введённой вами команды не существует. Попробуйте ввести другую команду.");
                    e.printStackTrace();

                    this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, errorResponse));

                } catch (NoSuchElementException e) {
                    //throw new ExitException("кнтрл д момент...");
                } catch (Exception e) {
                    System.out.println(" ");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

