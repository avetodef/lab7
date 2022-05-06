package server;

import commands.ACommands;
import commands.CommandSaver;
import dao.DataBaseDAO;
import dao.RouteDAO;
import interaction.Response;
import json.JsonConverter;
import utils.RouteInfo;
import java.util.concurrent.RecursiveTask;

public class RequestProcessor extends RecursiveTask<Response>{

    private final String msg;
    private final RouteDAO dao;
    private final DataBaseDAO dataBaseDAO;

    public RequestProcessor(String msg, RouteDAO dao, DataBaseDAO dataBaseDAO) {
        this.msg = msg;
        this.dao = dao;
        this.dataBaseDAO = dataBaseDAO;
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

        return command.execute(dao, dataBaseDAO);
    }




}
