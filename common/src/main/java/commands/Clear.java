package commands;



import dao.RouteDAO;
import db.DataBaseDAO;
import exceptions.DataBaseException;
import interaction.Response;
import interaction.Status;
import utils.Route;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс команды CLEAR, предназначенный для очистки коллекции
 */
public class Clear extends ACommands{
    static Set<Integer> distanceSet = new HashSet<>();

    public Response execute(DataBaseDAO dao) {

            for (Route route : dao.getAll()) {
                distanceSet.add(route.getDistance());
            }
            for (int i = 1; i < dao.getAll().size() + 1; i++)
                dao.delete(i);
            dao.clear();
            distanceSet.clear();
        try {
            dao.clearCollection();
        } catch (DataBaseException e) {
            response.msg(e.getMessage()).status(Status.UNKNOWN_ERROR);
        } catch (SQLException e) {
            response.msg(e.getMessage()).status(Status.UNKNOWN_ERROR);
        }

        response.msg("ура удалилось")
                    .status(Status.OK);
            return response;
    }
}
