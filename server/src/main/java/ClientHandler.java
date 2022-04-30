import commands.Save;
import console.ConsoleOutputer;
import dao.RouteDAO;
import file.FileManager;
import interaction.Response;
import interaction.Status;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ConsoleOutputer output = new ConsoleOutputer();
    private final FileManager manager = new FileManager();
    private final RouteDAO dao = manager.read();

    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private ReentrantLock lock = new ReentrantLock();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override

    public void run() {

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

                    String clientMessage = this.fixedThreadPool.submit(new RequestReader(socketInputStream)).get();

                    Response serverResponse = forkJoinPool.invoke(new RequestProcessor(clientMessage, dao));

                    this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, serverResponse));

                    Save.execute(dao);

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

