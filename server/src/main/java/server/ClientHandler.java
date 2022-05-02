package server;

import commands.Save;
import console.ConsoleOutputer;
import dao.RouteDAO;
import db.DataBaseDAO;
import db.DataBaseHandler;
import db.DataBaseUsersDolboeb;
import file.FileManager;
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
    public DataBaseHandler dbHandler = new DataBaseHandler();
    public DataBaseUsersDolboeb dbUserDolboeb = new DataBaseUsersDolboeb();
    public DataBaseDAO dbdao = new DataBaseDAO(dbHandler, dbUserDolboeb);
    //TODO переписать на скл
    FileManager manager = new FileManager();
    private final RouteDAO dao = manager.read();

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

                    save.execute(dao);

                } catch (NullPointerException e) {
                    errorResponse.setMsg("Введённой вами команды не существует. Попробуйте ввести другую команду.");

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

