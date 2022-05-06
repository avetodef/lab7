package commands;



import dao.DataBaseDAO;
import dao.RouteDAO;
import interaction.Response;
import interaction.Status;
import utils.Route;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс команды CLEAR, предназначенный для очистки коллекции
 */
public class Clear extends ACommands{
    static Set<Integer> distanceSet = new HashSet<>();

    public Response execute(RouteDAO routeDAO, DataBaseDAO dbDAO) {

            for (Route route : routeDAO.getAll()) {
                distanceSet.add(route.getDistance());
            }
            for (int i = 1; i < routeDAO.getAll().size() + 1; i++)
                routeDAO.delete(i, routeDAO.get(i) );
            routeDAO.clear();
            distanceSet.clear();
            dbDAO.clear();

            response.msg("ура удалилось")
                    .status(Status.OK);
            return response;
    }
}
