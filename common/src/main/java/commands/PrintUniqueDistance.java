package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import interaction.Response;
import interaction.Status;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс команды PRINT UNIQUE DISTANCE, предназначенный для вывода значения уникального поля distance
 */
public class PrintUniqueDistance extends ACommands{

    static Set<Integer> distanceSet = new HashSet<>();

    public Response execute(DataBaseDAO dao) {
        if (dao.getAll() == null)
            response.msg("пусто").status(Status.COLLECTION_ERROR);
         else {
            dao.getAll().stream().forEach(r -> distanceSet.add(r.getDistance()));

            response.msg("уникальные значения поля distance: " + distanceSet.toString()).
                    status(Status.OK);

        }

        return response;
    }

}
