import commands.VideoRzhaka;
import console.ConsoleOutputer;
import console.ConsoleReader;
import console.Console;
import exceptions.EmptyInputException;
import exceptions.ExitException;
import interaction.Request;
import interaction.Response;
import interaction.User;
import json.JsonConverter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class ClientApp implements Runnable {

    ConsoleReader consoleReader = new ConsoleReader();
    ConsoleOutputer output = new ConsoleOutputer();
    Scanner sc = new Scanner(System.in);
    ByteBuffer buffer = ByteBuffer.allocate(60_000);
    Console console = new Console();
    int serverPort = 6666;
    List<String> input;
    String serverResponse;
    Request request;
    ReaderSender readerSender = new ReaderSender();
    User user;

    protected void mainClientLoop() {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", serverPort));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            if (!Authorization.isAuth) {
                user = Authorization.askIfAuth(sc);
            } else {
                go(selector, socketChannel, user);
            }

        }
        catch (UnknownHostException e) {
            System.err.println("неизвестный хост. порешай там в коде что нибудь ок?");
            System.exit(1);

        } catch (IOException exception) {
            System.err.println("Сервер пока недоступен. Закончить работу клиента? (напишите {yes} или {no})?");

            String answer;
            try {
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
                System.out.println("жди...");
            }catch (NoSuchElementException e){
                throw new ExitException("poka");
            }
        }
    }

    private void go(Selector selector, SocketChannel socketChannel, User user) throws IOException {

        while (true) {

            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                SocketChannel client = (SocketChannel) key.channel();

                if (key.isConnectable()) {

                    connect(client);
                    client.register(selector, SelectionKey.OP_WRITE);
                    continue;
                }

                if (key.isWritable()) {

                    try {
                        input = consoleReader.reader();

                        request = new Request(input, null, user);

                        ASCIIArt.ifExit(input, output);

                        if (input.contains("mega_rzhaka"))
                            new Thread(new VideoRzhaka()).start();

                        if (input.contains("execute_script")) {
                            readerSender.readAndSend(CommandChecker.ifExecuteScript(input), request, socketChannel, console);
                        } else {
                            readerSender.readAndSend(input, request, socketChannel, console);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("int введи");
                        continue;
                    } catch (NullPointerException e) {
                        output.printRed("Введённой вами команды не существует. Попробуйте ввести другую команду.");
                        continue;
                    } catch (EmptyInputException e) {
                        output.printRed(e.getMessage());
                        continue;
                    } catch (IndexOutOfBoundsException e) {
                        output.printRed("брат забыл айди ввести походу");
                        continue;
                    }
                    client.register(selector, SelectionKey.OP_READ);
                    continue;
                }

                if (key.isReadable()) {

                    read(socketChannel);

                    client.register(selector, SelectionKey.OP_WRITE);
                }

            }

        }
    }

    private void connect(SocketChannel client) {
        if (client.isConnectionPending()) {
            try {
                client.finishConnect();
                output.printWhite("готов к работе с сервером");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void read(SocketChannel socketChannel) {
        try {

            socketChannel.read(buffer);
            buffer.flip();

            serverResponse = StandardCharsets.UTF_8.decode(buffer).toString().substring(2);

            Response response = JsonConverter.desResponse(serverResponse);
            printPrettyResponse(response);
            buffer.clear();

        } catch (IOException e) {
        }
    }


    protected void runClient() {

        while (true) {
            try {
                mainClientLoop();
            } catch (ExitException e) {
                System.err.println(e.getMessage());
                break;
            } catch (RuntimeException e) {
                System.err.println("ошибка.....: " + e.getMessage());
            }
        }

    }


    private void printPrettyResponse(Response r) {
        switch (r.status) {
            case OK: {
                output.printNormal(r.msg);
                break;
            }
            case FILE_ERROR: {
                output.printBlue(r.msg);
                break;
            }
            case UNKNOWN_ERROR: {
                output.printRed(r.msg);
                break;
            }
            case COLLECTION_ERROR: {
                output.printYellow(r.msg);
                break;
            }
            case USER_EBLAN_ERROR: {
                output.printPurple(r.msg);
                break;
            }
            case SERVER_ERROR: {
                output.printRed(r.msg);
            }
        }
    }

    private void serverUpal() {
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
        System.out.println("жди...");
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

        runClient();
    }
}