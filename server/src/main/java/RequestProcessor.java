import commands.ACommands;
import commands.CommandSaver;
import dao.RouteDAO;
import interaction.Request;
import interaction.Response;
import json.JsonConverter;
import java.util.concurrent.RecursiveTask;

public class RequestProcessor extends RecursiveTask<Response>{

    private final String msg;
    private final RouteDAO dao;


    public RequestProcessor(String msg, RouteDAO dao) {
        this.msg = msg;
        this.dao = dao;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Response compute() {
        return executeCommand(CommandSaver.getCommand(getRequestFromString(msg).getArgs()));
    }

    private Response executeCommand(ACommands command) {
        return command.execute(dao);
    }

    private Request getRequestFromString(String msg){
        return JsonConverter.des(msg);
    }

}
