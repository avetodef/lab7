package server;

import commands.ACommands;
import commands.CommandSaver;
import db.DataBaseDAO;
import interaction.Response;
import json.JsonConverter;
import utils.RouteInfo;

import java.io.DataOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;

public class RequestProcessor extends RecursiveTask<Response>{

    private final String msg;
    private final DataBaseDAO dao;
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private final DataOutputStream dataOutputStream;

    public RequestProcessor(String msg, DataBaseDAO dao, DataOutputStream dataOutputStream) {
        this.msg = msg;
        this.dao = dao;
        this.dataOutputStream = dataOutputStream;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Response compute() {

        ACommands command = CommandSaver.getCommand(JsonConverter.des(msg).getArgs());
        RouteInfo info = JsonConverter.des(msg).getInfo();
        command.setInfo(info);
        command.setUser(JsonConverter.des(msg).getUser());

        this.fixedThreadPool.submit(new ResponseSender(dataOutputStream, command.execute(dao)));

        return command.execute(dao);
    }




}
