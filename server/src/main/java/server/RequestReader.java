package server;

import db.DataBaseDAO;
import db.TableManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;


public class RequestReader implements Callable<String> {

    private final InputStream socketInputStream;
    private ForkJoinPool forkJoinPool = new ForkJoinPool(3);
    private TableManager tableManager = new TableManager();
    private DataBaseDAO dao = tableManager.read();
    private final DataOutputStream dataOutputStream;

    public RequestReader(InputStream socketInputStream, TableManager tableManager, DataBaseDAO dao, ForkJoinPool forkJoinPool, DataOutputStream dataOutputStream) {
        this.socketInputStream = socketInputStream;
        this.tableManager = tableManager;
        this.dao = dao;
        this.forkJoinPool = forkJoinPool;
        this.dataOutputStream = dataOutputStream;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */

    @Override
    public String call() {
        try {
            String requestJson;
            StringBuilder builder = new StringBuilder();

            int byteRead;

            while ((byteRead = socketInputStream.read()) != -1) {
                if (byteRead == 0) break;
                builder.append((char) byteRead);
            }
            requestJson = builder.toString();

            forkJoinPool.invoke(new RequestProcessor(requestJson, dao, dataOutputStream));
            return "done successfully";
        } catch (SocketException e) {
            System.out.println("клиент лег поспать. жди.");
            while (true) {
            }

        } catch (IOException e) {
            System.out.println("server razuchilsya chitat... wot pochemy: " + e.getMessage());

        } catch (NullPointerException e) {
            System.out.println("stalo pusto v dushe i v request'e: " + e.getMessage());
        }
        return null;
    }
}
