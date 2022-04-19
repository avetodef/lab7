package commands;


import dao.RouteDAO;
import interaction.Response;
import interaction.Status;

/**
 * Класс команды INFO, предназначенный для вывода информации об элементах коллекции. Вывод осуществляется с помощью команды getDescription.
 */
public class Info extends ACommands {

    public Response execute(RouteDAO routeDAO) {

        response.msg(routeDAO.toString()).status(Status.OK);

        return response;
    }
}
