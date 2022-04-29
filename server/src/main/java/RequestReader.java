import commands.ACommands;
import exceptions.ExitException;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class RequestReader implements Callable<String>{

    private InputStream stream;

    public RequestReader(InputStream stream) {
        this.stream = stream;
    }

//    private String read(){
//        try {
//            String requestJson;
//            StringBuilder builder = new StringBuilder();
//
//            int byteRead;
//
//            while ((byteRead = stream.read()) != -1) {
//                if (byteRead == 0) break;
//                builder.append((char) byteRead);
//            }
//
//            requestJson = builder.toString();
//
//
//            return requestJson;
//
//        } catch (SocketException e) {
//            System.out.println("клиент лег поспать");
//            try {
//                Scanner sc = new Scanner(System.in);
//                String s;
//                //System.out.println("что то упало... может закончим все это? {y/n}");
//                //System.out.println("что то упало..жди");
//
//                while (!(s = sc.nextLine()).equals("n")) {
//                    switch (s) {
//                        case "":
//                            break;
//                        case "y":
//                            System.exit(0);
//                            break;
//                        default:
//                            System.out.println("клиент спит. не буди его");
//                    }
//                }
//
//            } catch (NoSuchElementException e1) {
//                throw new ExitException("кнтрл д момент. ну лови исключение значит");
////                System.out.println("кнтрл д момент...");
////                System.exit(1);
//            }
//
//        } catch (IOException e) {
//            System.out.println("server razuchilsya chitat... wot pochemy: " + e.getMessage());
//            //e.printStackTrace();
//        } catch (NullPointerException e) {
//            System.out.println("stalo pusto v dushe i v request'e: " + e.getMessage());
//            while (true) {
//
//            }
//        }
//        return null;
//    }

    @Override
    public String call() throws Exception {
        try {
            String requestJson;
            StringBuilder builder = new StringBuilder();

            int byteRead;

            while ((byteRead = stream.read()) != -1) {
                if (byteRead == 0) break;
                builder.append((char) byteRead);
            }

            requestJson = builder.toString();


            return requestJson;

        } catch (SocketException e) {
            System.out.println("клиент лег поспать");
            try {
                Scanner sc = new Scanner(System.in);
                String s;
                //System.out.println("что то упало... может закончим все это? {y/n}");
                //System.out.println("что то упало..жди");

                while (!(s = sc.nextLine()).equals("n")) {
                    switch (s) {
                        case "":
                            break;
                        case "y":
                            System.exit(0);
                            break;
                        default:
                            System.out.println("клиент спит. не буди его");
                    }
                }

            } catch (NoSuchElementException e1) {
                throw new ExitException("кнтрл д момент. ну лови исключение значит");
//                System.out.println("кнтрл д момент...");
//                System.exit(1);
            }

        } catch (IOException e) {
            System.out.println("server razuchilsya chitat... wot pochemy: " + e.getMessage());
            //e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("stalo pusto v dushe i v request'e: " + e.getMessage());
            while (true) {

            }
        }
        return null;
    }
}

//public class RequestReader extends Thread {
//    private InputStream socketInputStream;
//
//    public RequestReader(InputStream socketInputStream) {
//        this.socketInputStream = socketInputStream;
//    }
//
//    public void run() {
//        read();
//        //TODO понять каким образом чтение должно работать если метод run void
//    }
//
//
//    protected String read() {
//
//        try {
//            String requestJson;
//            StringBuilder builder = new StringBuilder();
//
//            int byteRead;
//
//            while ((byteRead = socketInputStream.read()) != -1) {
//                if (byteRead == 0) break;
//                builder.append((char) byteRead);
//            }
//
//            requestJson = builder.toString();
//
//
//            return requestJson;
//
//        } catch (SocketException e) {
//            System.out.println("клиент лег поспать");
//            try {
//                Scanner sc = new Scanner(System.in);
//                String s;
//                //System.out.println("что то упало... может закончим все это? {y/n}");
//                //System.out.println("что то упало..жди");
//
//                while (!(s = sc.nextLine()).equals("n")) {
//                    switch (s) {
//                        case "":
//                            break;
//                        case "y":
//                            System.exit(0);
//                            break;
//                        default:
//                            System.out.println("клиент спит. не буди его");
//                    }
//                }
//
//            } catch (NoSuchElementException e1) {
//                throw new ExitException("кнтрл д момент. ну лови исключение значит");
////                System.out.println("кнтрл д момент...");
////                System.exit(1);
//            }
//
//        } catch (IOException e) {
//            System.out.println("server razuchilsya chitat... wot pochemy: " + e.getMessage());
//            //e.printStackTrace();
//        } catch (NullPointerException e) {
//            System.out.println("stalo pusto v dushe i v request'e: " + e.getMessage());
//            while (true) {
//
//            }
//        }
//        return null;
//    }
//}
