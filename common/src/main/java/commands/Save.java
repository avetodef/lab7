package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import db.DataBaseHandler;
import file.FileManager;
import interaction.Response;
import interaction.Status;

import java.io.IOException;

/**
 * Класс команды SAVE, предназначенный для сохранения элементов в коллекцию
 */
public class Save extends ACommands {

    public Response execute(DataBaseDAO dao) {
            try {
                DataBaseHandler handler = new DataBaseHandler();
                handler.saveSQL();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            response.msg("сохранено").status(Status.OK);

            return response;
        }
    }
