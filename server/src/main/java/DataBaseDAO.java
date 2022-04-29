import dao.DAO;
import utils.Route;
import utils.RouteInfo;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Deque;

public class DataBaseDAO implements DAO {
//TODO прописать методы для работы с эскуэль (новое дао(говно))


    //ROUTE
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


    //COORDINATES
    private final String SELECT_ALL_COORDINATES_TABLE = "SELECT * FROM" + DataBaseHandler.COORDINATES_TABLE;

    private final String SELECT_COORDINATES_BY_ID = SELECT_ALL_COORDINATES_TABLE + "WHERE"
            + DataBaseHandler.COORDINATES_TABLE_ID_COLUMN + "=?";

    private final String INSERT_COORDINATES_TABLE = "INSERT INTO" + DataBaseHandler.COORDINATES_TABLE + " ("
            + DataBaseHandler.COORDINATES_TABLE_ID_COLUMN + " ," + DataBaseHandler.COORDINATES_TABLE_X_COLUMN + ","
            + DataBaseHandler.COORDINATES_TABLE_Y_COLUMN + " ) VALUES(?,?,?)";

    private final String REMOVE_COORDINATES_BY_ID = "DELETE FROM" + DataBaseHandler.COORDINATES_TABLE
            + "WHERE" + DataBaseHandler.COORDINATES_TABLE_ID_COLUMN + " =?";

    private final String UPDATE_COORDINATES_BY_ID = "UPDATE" + DataBaseHandler.COORDINATES_TABLE + "WHERE"
            + DataBaseHandler.COORDINATES_TABLE_ID_COLUMN + "=?" + DataBaseHandler.COORDINATES_TABLE_X_COLUMN
            + "=?" + "WHERE" + DataBaseHandler.COORDINATES_TABLE_Y_COLUMN + "=?";


    //LOCATION_FROM
    private final String SELECT_ALL_LOCATION_FROM_TABLE = "SELECT * FROM" + DataBaseHandler.LOCATION_FROM_TABLE;

    private final String SELECT_LOCATION_FROM_BY_ID = SELECT_ALL_LOCATION_FROM_TABLE + "WHERE"
            + DataBaseHandler.LOCATION_FROM_TABLE_ID_COLUMN + "=?";

    private final String INSERT_LOCATION_FROM_TABLE = "INSERT INTO" + DataBaseHandler.LOCATION_FROM_TABLE + " ("
            + DataBaseHandler.LOCATION_FROM_TABLE_ID_COLUMN + " ," + DataBaseHandler.LOCATION_FROM_TABLE_X_COLUMN + ","
            + DataBaseHandler.LOCATION_FROM_TABLE_Y_COLUMN + "," + DataBaseHandler.LOCATION_FROM_TABLE_NAME_COLUMN
            + " ) VALUES(?,?,?,?)";

    private final String REMOVE_LOCATION_FROM_BY_ID = "DELETE FROM" + DataBaseHandler.LOCATION_FROM_TABLE
            + "WHERE" + DataBaseHandler.LOCATION_FROM_TABLE_ID_COLUMN + " =?";

    private final String UPDATE_LOCATION_FROM_BY_ID = "UPDATE" + DataBaseHandler.LOCATION_FROM_TABLE + "WHERE"
            + DataBaseHandler.LOCATION_FROM_TABLE_ID_COLUMN + "=?" + DataBaseHandler.LOCATION_FROM_TABLE_X_COLUMN
            + "=?" + DataBaseHandler.LOCATION_FROM_TABLE_Y_COLUMN + "?" + "WHERE"
            + DataBaseHandler.LOCATION_FROM_TABLE_NAME_COLUMN + "=?";


    //LOCATION_TO
    private final String SELECT_ALL_LOCATION_TO_TABLE = "SELECT * FROM" + DataBaseHandler.LOCATION_TO_TABLE;

    private final String SELECT_LOCATION_TO_BY_ID = SELECT_ALL_LOCATION_TO_TABLE + "WHERE"
            + DataBaseHandler.LOCATION_TO_TABLE_ID_COLUMN + "=?";

    private final String INSERT_LOCATION_TO_TABLE = "INSERT INTO" + DataBaseHandler.LOCATION_TO_TABLE + " ("
            + DataBaseHandler.LOCATION_TO_TABLE_ID_COLUMN + " ," + DataBaseHandler.LOCATION_TO_TABLE_X_COLUMN + ","
            + DataBaseHandler.LOCATION_TO_TABLE_Y_COLUMN + "," + DataBaseHandler.LOCATION_TO_TABLE_NAME_COLUMN
            + " ) VALUES(?,?,?,?)";

    private final String REMOVE_LOCATION_TO_BY_ID = "DELETE FROM" + DataBaseHandler.LOCATION_TO_TABLE
            + "WHERE" + DataBaseHandler.LOCATION_TO_TABLE_ID_COLUMN + " =?";

    private final String UPDATE_LOCATION_TO_BY_ID = "UPDATE" + DataBaseHandler.LOCATION_TO_TABLE + "WHERE"
            + DataBaseHandler.LOCATION_TO_TABLE_ID_COLUMN + "=?" + DataBaseHandler.LOCATION_TO_TABLE_X_COLUMN
            + "=?" + DataBaseHandler.LOCATION_TO_TABLE_Y_COLUMN + "?" + "WHERE"
            + DataBaseHandler.LOCATION_TO_TABLE_NAME_COLUMN + "=?";


    private Route createRoute(ResultSet resultSet) throws SQLException {
        //id
        int id = resultSet.getInt(DataBaseHandler.ROUTE_TABLE);
        //name
        String name = resultSet.getString(DataBaseHandler.ROUTE_TABLE_NAME_COLUMN);
        //x double
        double x = resultSet.getDouble(DataBaseHandler.COORDINATES_TABLE_X_COLUMN);
        //y Double
        Double y = resultSet.getDouble(DataBaseHandler.COORDINATES_TABLE_Y_COLUMN);

        //date ZonedDataTime
        Time creationdate = resultSet.getTime(DataBaseHandler.ROUTE_TABLE_DATE_COLUMN);

        //from-x double
        double from_x = resultSet.getDouble(DataBaseHandler.LOCATION_FROM_TABLE_X_COLUMN);

        //from-y Long
        Long from_y = resultSet.getLong(DataBaseHandler.LOCATION_FROM_TABLE_Y_COLUMN);

        //name-from String
        String name_from = resultSet.getString(DataBaseHandler.LOCATION_FROM_TABLE_NAME_COLUMN);

        //to-x int
        int to_x = resultSet.getInt(DataBaseHandler.LOCATION_TO_TABLE_X_COLUMN);

        //to-y float
        float to_y = resultSet.getFloat(DataBaseHandler.LOCATION_TO_TABLE_Y_COLUMN);

        //name-to String
        String name_to = resultSet.getString(DataBaseHandler.LOCATION_TO_TABLE_NAME_COLUMN);

        //distance Integer
        Integer distance = resultSet.getInt(DataBaseHandler.ROUTE_TABLE_DISTANCE_COLUMN);
        return new Route(
                id,
                name,
                x,
                y,
                creationdate,
                from_x,
                from_y,
                name_from,
                to_x,
                to_y,
                name_to,
                distance
        );

    }










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
