package commands;



import dao.RouteDAO;
import db.DataBaseDAO;
import db.DataBaseHandler;
import db.DataBaseUsersDolboeb;
import interaction.Request;
import interaction.Response;
import utils.RouteInfo;

import java.util.List;

public abstract class ACommands {

    public List<String> args;

    public void addArgs(List<String> args) {
        this.args = args;
    }

    public abstract Response execute(RouteDAO routeDAO);

    protected boolean isAsker;
    protected boolean isIdAsker;
    protected RouteInfo info;

    public void setInfo(RouteInfo info) {
        this.info = info;
    }

    public static ACommands getCommand(Request request) {
        ACommands command = CommandSaver.getCommand(request.getArgs());
        command.setInfo(request.getInfo());
        return command;
    }

    public boolean isAsker() {
        return isAsker;
    }

    public boolean isIdAsker() {
        return isIdAsker;
    }

    public Response response = new Response();
    public DataBaseHandler dbHandler = new DataBaseHandler();
    public DataBaseUsersDolboeb dbUserDolboeb = new DataBaseUsersDolboeb();

    public DataBaseDAO dbdao = new DataBaseDAO(dbHandler, dbUserDolboeb);


}
