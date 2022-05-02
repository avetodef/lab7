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
//TODO мне кажется тут будет не то что надо...есть такое подозрение
        response.msg(dao.toString()).status(Status.OK);

        return response;
    }
}
