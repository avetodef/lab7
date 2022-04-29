import dao.DAO;
import utils.Route;
import utils.RouteInfo;

import java.util.Deque;

public class DataBaseDAO implements DAO {
//TODO прописать методы для работы с эскуэль (новое дао(говно))
    //Управление коллекцией базы данных
    private final String SELECT_ALL_ROUTE = "SELECT * FROM " + DataBaseHandler.ROUTE_TABLE;
    private final String SELECT_ROUTE_BY_ID = SELECT_ALL_ROUTE
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "= ?";
    private final String SELECT_ROUTE_BY_USER_AND_ELEMENT_ID = SELECT_ROUTE_BY_ID
            + "AND" + DataBaseHandler.ROUTE_TABLE_USER_ID_COLUMN + "= ?";
    private final String INSERT_INTO_ROUTE = "INSERT INTO" + DataBaseHandler.ROUTE_TABLE + "("
            + DataBaseHandler.ROUTE_TABLE_NAME_COLUMN + "," + DataBaseHandler.ROUTE_TABLE_DATE_COLUMN + ","
            + DataBaseHandler.ROUTE_TABLE_DISTANCE_COLUMN + "," + DataBaseHandler.ROUTE_TABLE_NAME_DISTANCE_COLUMN
            + "," + DataBaseHandler.ROUTE_TABLE_USER_ID_COLUMN + ") VALUES(? ? ? ? ?)";
    private final String REMOVE_ROUTE_BY_ID = "DELETE FROM" + DataBaseHandler.ROUTE_TABLE
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";
    private final String UPDATE_ROUTE_NAME_BY_ID = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "SET" + DataBaseHandler.ROUTE_TABLE_NAME_COLUMN + "= ?"
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";
    private final String UPDATE_ROUTE_DISTANCE_BY_ID = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "SET" + DataBaseHandler.ROUTE_TABLE_DISTANCE_COLUMN + "=?"
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";
    private final String UPDATE_ROUTE_NAME_DISTANCE_BY_ID = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "SET" + DataBaseHandler.ROUTE_TABLE_NAME_DISTANCE_COLUMN + "=?"
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";
    private final String UPDATE_ROUTE_USER_ID_COLUMN = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "SET" + DataBaseHandler.ROUTE_TABLE_USER_ID_COLUMN + "=?"
            + "WHERE" + DataBaseHandler.USER_TABLE_ID_COLUMN + "=?";





    @Override
    public void create(Route route) {
    }

    @Override
    public boolean update(int id, RouteInfo routeInfo) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Deque<Route> getAll() {
        return null;
    }

    @Override
    public void clear() {

    }
}
