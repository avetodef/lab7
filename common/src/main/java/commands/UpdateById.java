package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import exceptions.DataBaseException;
import interaction.Response;
import interaction.Status;
import utils.Route;

import java.sql.SQLException;

/**
 * Класс команды UPDATE BY ID, предназначенный для обновления элемента по его id.
 *
 * @param
 */
public class UpdateById extends ACommands {
    {
        isAsker = true;
        isIdAsker = true;
    }


    public Response execute(DataBaseDAO dao) {

        int idFromConsole = Integer.parseInt(args.get(1));
        if (dao.getAll().size() == 0)
            response.msg("пусто. нечего обновлять").status(Status.COLLECTION_ERROR);

        else {

            if (!checkId(idFromConsole, dao))
                response.status(Status.USER_EBLAN_ERROR).msg("элемента с таким id нет. ведите другой id");

            else {
                try {
                    Route route = new Route(info.name, info.x, info.y, info.fromX,
                            info.fromY, info.nameFrom, info.toX, info.toY, info.nameTo,
                            info.distance);

                    dao.updateRouteId(idFromConsole, route);
                } catch (IndexOutOfBoundsException e) {
                    response.msg("брат забыл айди ввести походу").status(Status.USER_EBLAN_ERROR);
                } catch (NumberFormatException e) {
                    response.msg("леее почему не int ввел братан").status(Status.USER_EBLAN_ERROR);

                } catch (RuntimeException e) {
                    response.msg("чета проихошло..." + e.getMessage()).status(Status.UNKNOWN_ERROR);

                } catch (SQLException throwables) {

                } catch (DataBaseException e) {

                }
                response.msg("элемент коллекции обновлен").status(Status.OK);

            }

        }
        return response;
    }

    private boolean checkId(int id, DataBaseDAO routeDAO) {

        for (Route route : routeDAO.getAll()) {
            if (route.getId() == id) {
                return true;
            }
        }
        return false;
    }

}
