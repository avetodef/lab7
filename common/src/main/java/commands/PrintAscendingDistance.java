package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import interaction.Response;
import interaction.Status;

/**
 * Класс команды PRINT ASCENDING DISTANCE, предназначенный для вывода значений поля distance в порядке возрастания
 */
public class PrintAscendingDistance extends ACommands{


    public Response execute(DataBaseDAO dao) {

        StringBuilder builder = new StringBuilder();
        dao.getAll().stream()
                .sorted((r1, r2) -> r2.getDistance() - r1.getDistance())
                .forEach(r->builder.append(r.getDistance()).append(" "));


        if (dao.getAll() == null)
            response.status(Status.COLLECTION_ERROR).msg("коллекция пустая. нечего выводить");
        else
            response.msg("значения поля distance всех элементов в порядке возрастания: " + builder).status(Status.OK);

        return response;
    }
}
