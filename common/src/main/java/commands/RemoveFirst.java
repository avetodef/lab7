package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import exceptions.DataBaseException;
import interaction.Response;
import interaction.Status;

import java.sql.SQLException;

/**
 * Класс команды REMOVE FIRST, предназначенный для удаления первого элемента из коллекции
 */
public class RemoveFirst extends ACommands{

    public Response execute(DataBaseDAO dao) {
        if (dao.getAll().size() == 0)
            response.status(Status.COLLECTION_ERROR).msg("коллекция пустая. нечего удалять");
            else {
            try {
                dao.removeById(dao.getAll().getFirst().getId());

            } catch (DataBaseException e) {
            } catch (SQLException e) {
            }
            response.msg("первый элемент удалился ура")
                            .status(Status.OK);

        }
        return response;
    }

}
