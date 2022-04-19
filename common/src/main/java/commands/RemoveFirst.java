package commands;


import dao.RouteDAO;
import interaction.Response;
import interaction.Status;

/**
 * Класс команды REMOVE FIRST, предназначенный для удаления первого элемента из коллекции
 */
public class RemoveFirst extends ACommands{

    public Response execute(RouteDAO routeDAO) {
        if (routeDAO.getAll().size() == 0)
            response.status(Status.COLLECTION_ERROR).msg("коллекция пустая. нечего удалять");
            else {
            routeDAO.removeFirst();
            response.msg("первый элемент удалился ура")
                            .status(Status.OK);

        }
        return response;
    }

}
