package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import exceptions.DataBaseException;
import interaction.Response;
import interaction.Status;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Класс команды SHOW, предназначенный для вывода коллекции на консоль
 */
public class Show extends ACommands {

    @Override
    public Response execute(DataBaseDAO dao) {
        if (dao.getAll() == null) {
            response.msg("пусто").status(Status.COLLECTION_ERROR);

        }
        else {
            try {
                response.status(Status.OK).msg(dao.getCollection().toString());
            } catch (DataBaseException | SQLException e) {
                System.out.println(" ");
            }
        }

        return response;
    }

}