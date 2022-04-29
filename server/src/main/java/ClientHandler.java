import commands.Save;
import console.ConsoleOutputer;
import dao.RouteDAO;
import file.FileManager;
import interaction.Response;
import interaction.Status;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ConsoleOutputer output = new ConsoleOutputer();
    private final FileManager manager = new FileManager();
    private final RouteDAO dao = manager.read();
    private final ReentrantLock lock = new ReentrantLock();
    private Future<String> clientMessage;

    //private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public String toString() {
        return "" + clientMessage;
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
                    //TODO через fixed thread pool сделать чтение

                    lock.lock();
                    try {
                        String clientMessage = new RequestReader(socketInputStream).call();
                        //clientMessage = fixedThreadPool.submit(new RequestReader(socketInputStream));


                        Response serverResponse = forkJoinPool.invoke(new RequestProcessor(clientMessage.toString(), dao));

                        this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, serverResponse));

                    }
                    catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                    } finally {
                        lock.unlock();
                    }
                      //fixedThreadPool.submit(new RequestReader(socketInputStream)); - тут падает, на рандоме обрезает реквест почему то...

//                    this.fixedThreadPool.execute(new RequestReader(socketInputStream));

//                    Future<String> clientMessage = fixedThreadPool.submit(()   ->
//                            new String(socketInputStream.readAllBytes().toString()));

                    //Response serverResponse = forkJoinPool.invoke(new RequestProcessor(clientMessage, dao));
//                    this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, serverResponse));

                    Save.execute(dao);


                } catch (NullPointerException e) {
                    errorResponse.setMsg("Введённой вами команды не существует. Попробуйте ввести другую команду."
                                    + e.getLocalizedMessage() );

                    this.fixedThreadPool.execute(new ResponseSender(clientSocket, socketOutputStream, dataOutputStream, errorResponse));

                } catch (NoSuchElementException e) {
                    //throw new ExitException("кнтрл д момент...");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

