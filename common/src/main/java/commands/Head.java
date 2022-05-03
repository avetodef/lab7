package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import interaction.Response;
import interaction.Status;

/**
 * Класс команды HEAD, предназначенный для вывода первого элемента коллекции
 */
public class Head extends ACommands{

    public Response execute(DataBaseDAO dao) {
        if (dao.getAll() == null) {

            response.msg("пусто...").status(Status.COLLECTION_ERROR);
        } else {

            response.msg(dao.getAll().getFirst().toString()).status(Status.OK);
        }

        return response;
    }

}
