package commands;


import dao.RouteDAO;
import interaction.Response;
import interaction.Status;
import utils.Route;

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


    public Response execute(RouteDAO routeDAO) {

        int idFromConsole = Integer.parseInt(args.get(1));
        if (routeDAO.getAll().size() == 0)
            response.msg("пусто. нечего обновлять").status(Status.COLLECTION_ERROR);

        else {

            if (!checkId(idFromConsole, routeDAO))
                response.status(Status.USER_EBLAN_ERROR).msg("элемента с таким id нет. ведите другой id");

            else {
                try {
                    routeDAO.update(idFromConsole, info);
                } catch (IndexOutOfBoundsException e) {
                    response.msg("брат забыл айди ввести походу").status(Status.USER_EBLAN_ERROR);
                } catch (NumberFormatException e) {
                    response.msg("леее почему не int ввел братан").status(Status.USER_EBLAN_ERROR);

                } catch (RuntimeException e) {
                    response.msg("чета проихошло..." + e.getMessage()).status(Status.UNKNOWN_ERROR);

                }
                response.msg("элемент коллекции обновлен").status(Status.OK);

            }

        }
        return response;
    }

    private boolean checkId(int id, RouteDAO routeDAO) {

        for (Route route : routeDAO.getAll()) {
            if (route.getId() == id) {
                return true;
            }
        }
        return false;
    }

}
