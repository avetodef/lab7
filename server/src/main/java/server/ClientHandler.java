package server;

import commands.Save;
import console.ConsoleOutputer;
import db.DataBaseDAO;
import db.TableManager;
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
    private TableManager tableManager = new TableManager();

    private DataBaseDAO dao = tableManager.read();

    private final ForkJoinPool forkJoinPool = new ForkJoinPool(3);
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private final Lock locker = new ReentrantLock();

    public Request request;
    public User user;
    private Save save = new Save();

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

//                    locker.lock();
//                    String clientMessage = this.fixedThreadPool.submit(new RequestReader(socketInputStream)).get();
//                    locker.unlock();


                    //locker.lock();
                    String msg = fixedThreadPool.submit(new RequestReader(socketInputStream, tableManager, dao, forkJoinPool, dataOutputStream)).get();
                    //locker.unlock();
                    System.out.println(msg);


//TODO вот здесь получен юзер
//                    request = JsonConverter.des(clientMessage);
//                    user = request.getUser();
//
//                    locker.lock();
//                    Response serverResponse = this.forkJoinPool.invoke(new RequestProcessor(clientMessage, dao));
//                    locker.unlock();

//                    locker.lock();
//                    this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, serverResponse));
//                    locker.unlock();
                    //TODO выкидывает нуллпоинтер, наверное потому что у меня нет бд
                    try {
                        save.execute();
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }

                } catch (NullPointerException e) {
                    errorResponse.setMsg("Введённой вами команды не существует. Попробуйте ввести другую команду.");

                    this.fixedThreadPool.execute(new ResponseSender(dataOutputStream, errorResponse));

                } catch (NoSuchElementException e) {
                    //throw new ExitException("кнтрл д момент...");
                } catch (Exception e) {
                    System.out.println(e.getMessage() + "Exc clientHandler");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "IO clientHandler");
        }
    }
}

