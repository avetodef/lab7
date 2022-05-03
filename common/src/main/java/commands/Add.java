package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import exceptions.DataBaseException;
import exceptions.ExitException;
import interaction.Response;
import interaction.Status;

import utils.Route;

import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Класс команды ADD, предназначенный для добавления элемента в коллекцию
 */
public class Add extends ACommands{
    {
        isAsker = true;
    }
    public Response execute(DataBaseDAO dao) {

        try {

            Route route = new Route(info.name, info.x, info.y, info.fromX,
                    info.fromY, info.nameFrom, info.toX, info.toY, info.nameTo,
                    info.distance);

            dao.insertRoute(route, user); //TODO здесь падает

            response.msg("элемент добавлен в коллекцию").status(Status.OK);
        }
        catch (NoSuchElementException e){throw new ExitException(e.getMessage());}

        catch (NullPointerException e){
            response.msg("ошибка..." + e.getMessage()).status(Status.COLLECTION_ERROR);
            //e.printStackTrace();
        }

        catch (RuntimeException e) {
            response.msg("невозможно добавить элемент в коллекцию" + e.getMessage()).status(Status.COLLECTION_ERROR);
e.printStackTrace();
        }
        catch (SQLException throwables) {
            //throwables.printStackTrace();
            response.msg(throwables.getMessage()).status(Status.UNKNOWN_ERROR);
        throwables.printStackTrace();
        }

        catch (DataBaseException e) {
            response.msg(e.getMessage()).status(Status.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        //response.msg("я дебил даже не зашел в цикл").status(Status.SERVER_ERROR);
        //:(
        return response;
    }

    @Override
    public String toString() {
        return "Add";
    }
}
