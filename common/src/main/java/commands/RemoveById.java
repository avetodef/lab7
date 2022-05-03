package commands;

import db.DataBaseDAO;
import exceptions.DataBaseException;
import interaction.Response;
import interaction.Status;

import java.sql.SQLException;

/**
 * Класс команды REMOVE BY ID, предназначенный для удаления элемента по его id
 *
 * @param
 */
public class RemoveById extends ACommands {
    {
        isIdAsker = true;
    }
    public Response execute(DataBaseDAO dao) {
        if (dao.getAll() == null) {
            response.setMsg("коллекция пустая. нечего удалять");
            response.setStatus(Status.COLLECTION_ERROR);
        } else {
            try {
                int id = Integer.parseInt(args.get(1));
                //TODO тоже не уверена насколько это будет работать
                if (!dao.removeById(id))
                    response.msg("нет элемента с таким id. введите команду заново с правильным id" ).
                            status(Status.USER_EBLAN_ERROR);
                 else
                     response.msg("ура удалилось").status(Status.OK);

            }
            catch (IndexOutOfBoundsException e){
                response.status(Status.USER_EBLAN_ERROR).msg("брат забыл айди ввести походу ");
            }
            catch (NumberFormatException e){
                response.msg("леее почему не int ввел братан").status(Status.USER_EBLAN_ERROR);

            }
            catch (RuntimeException e) {
                response.msg("непредвиденная ошибка в классе команды: " + e.getMessage())
                        .status(Status.UNKNOWN_ERROR);
            } catch (SQLException throwables) {
                response.msg(throwables.getMessage()).status(Status.UNKNOWN_ERROR);
            } catch (DataBaseException e) {
                response.msg(e.getMessage()).status(Status.UNKNOWN_ERROR);
            }

        }
        return response;
    }

}
