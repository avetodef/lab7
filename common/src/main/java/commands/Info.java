package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import interaction.Response;
import interaction.Status;

/**
 * Класс команды INFO, предназначенный для вывода информации об элементах коллекции. Вывод осуществляется с помощью команды getDescription.
 */
public class Info extends ACommands {

    public Response execute(DataBaseDAO dao) {
//TODO по другому написать команду инфо
        try {
            response.msg("Route {" +
                    "size: " + dao.getAll().size() + "type: ArrayDequeue }").status(Status.OK);
        }
        catch (NullPointerException e){
            response.msg("Route {" +
                    "size: 0" + "type: ArrayDequeue }").status(Status.OK);
        }
        return response;
    }
}
