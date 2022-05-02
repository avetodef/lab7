package db;

import dao.DAO;
import dao.RouteDAO;
import exceptions.DataBaseException;
import interaction.User;
import utils.Route;
import utils.RouteInfo;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Deque;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Класс-дао для базы данных и коллекции
 */
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
            + DataBaseHandler.ROUTE_TABLE_DISTANCE_COLUMN + "," + DataBaseHandler.ROUTE_TABLE_USER_ID_COLUMN
            + ") VALUES(? ? ? ? ?)";

    private final String REMOVE_ROUTE_BY_ID = "DELETE FROM" + DataBaseHandler.ROUTE_TABLE
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";

    private final String UPDATE_ROUTE_BY_ID = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";

    private final String UPDATE_ROUTE_NAME_BY_ID = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "SET" + DataBaseHandler.ROUTE_TABLE_NAME_COLUMN + "= ?"
            + "WHERE" + DataBaseHandler.ROUTE_TABLE_ID_COLUMN + "=?";

    private final String UPDATE_ROUTE_DISTANCE_BY_ID = "UPDATE" + DataBaseHandler.ROUTE_TABLE
            + "SET" + DataBaseHandler.ROUTE_TABLE_DISTANCE_COLUMN + "=?"
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

    private DataBaseHandler dataBaseHandler;
    private DataBaseUsersDolboeb dataBaseUsersDolboeb;


    public DataBaseDAO(DataBaseHandler dataBaseHandler, DataBaseUsersDolboeb dataBaseUsersDolboeb) {
        this.dataBaseHandler = dataBaseHandler;
        this.dataBaseUsersDolboeb = dataBaseUsersDolboeb;
    }

    /**
     * Класс, отвечающий за создание коллекции с элементами определенного типа
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */

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

    /**
     * Класс, отвечающий за получение данных о коллекции
     *
     * @return
     * @throws DataBaseException
     */
    public NavigableSet<Route> getCollection() throws DataBaseException {
        NavigableSet<Route> routes = new TreeSet<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = dataBaseHandler.getPreparedStatement(SELECT_ALL_ROUTE, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                routes.add(createRoute(resultSet));
            }
        } catch (SQLException e) {
            throw new DataBaseException();
        } finally {
            dataBaseHandler.closePreparedStatement(preparedSelectAllStatement);
        }
        return routes;
    }

    /*private int getRouteId(int id) throws SQLException{
        int routeId;
        PreparedStatement preparedSelectRouteIdStatement = null;
        try{
            preparedSelectRouteIdStatement = dataBaseHandler.getPreparedStatement(SELECT_ROUTE_BY_ID,false);
            preparedSelectRouteIdStatement.setInt(1,id);
            ResultSet resultSet = preparedSelectRouteIdStatement.executeQuery();
            //System.out.println("Запрос выполнен");
            if (resultSet.next()){
                routeId = resultSet.getInt(DataBaseHandler.ROUTE_TABLE_ID_COLUMN);
            } else throw new SQLException();
        }catch (SQLException e){
            System.out.println("Не удалось выполнить данный запрос");
        } finally {
            dataBaseHandler.closePreparedStatement(preparedSelectRouteIdStatement);
        }
        return routeId;
    }*/

    /**
     * Класс, отвечающий за получение элемента по его id
     *
     * @param routeId
     * @return
     * @throws SQLException
     */
    private Route getRouteById(int routeId) throws SQLException {
        Route route;
        PreparedStatement preparedSelectRouteByIdStatement = null;
        try {
            preparedSelectRouteByIdStatement = dataBaseHandler.getPreparedStatement(SELECT_ROUTE_BY_ID, false);
            preparedSelectRouteByIdStatement.setInt(1, routeId);
            ResultSet resultSet = preparedSelectRouteByIdStatement.executeQuery();
            System.out.println("запрос выполнен");
            if (resultSet.next()) {
                route = new Route(
                        resultSet.getString(DataBaseHandler.ROUTE_TABLE_NAME_COLUMN),
                        resultSet.getInt(DataBaseHandler.ROUTE_TABLE_DISTANCE_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении данного запроса");
            throw new SQLException(exception);
        } finally {
            dataBaseHandler.closePreparedStatement(preparedSelectRouteByIdStatement);
        }
        return route;
    }

    /**
     * Класс, отвчеающий за добавление элемента
     *
     * @param route
     * @param user
     * @return
     * @throws DataBaseException
     * @throws SQLException
     */
    public Route insertRoute(Route route, User user) throws DataBaseException, SQLException {
        Route route1;
        PreparedStatement preparedInsertRouteStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertLocationFromStatement = null;
        PreparedStatement preparedInsertLocationToStatement = null;
        try {
            dataBaseHandler.setCommitMode();
            dataBaseHandler.saveSQL();

            ZonedDateTime creationDate = ZonedDateTime.now();

            preparedInsertRouteStatement = dataBaseHandler.getPreparedStatement(INSERT_INTO_ROUTE, true);
            preparedInsertCoordinatesStatement = dataBaseHandler.getPreparedStatement(INSERT_COORDINATES_TABLE, true);
            preparedInsertLocationFromStatement = dataBaseHandler.getPreparedStatement(INSERT_LOCATION_FROM_TABLE, true);
            preparedInsertLocationToStatement = dataBaseHandler.getPreparedStatement(INSERT_LOCATION_TO_TABLE, true);
            //TODO запрос хуйня переделывай
            //preparedInsertRouteStatement.setString(1,route.getName());

            preparedInsertRouteStatement.setString(1, route.getName());
            preparedInsertRouteStatement.setInt(6, route.getDistance());
            preparedInsertRouteStatement.setTime(2, Time.valueOf(String.valueOf(creationDate)));
            preparedInsertCoordinatesStatement.setString(3, route.getCoordinates().toString());
            preparedInsertLocationFromStatement.setString(4, route.getFrom().toString());
            preparedInsertLocationToStatement.setString(5, route.getTo().toString());

            if (preparedInsertRouteStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generateRouteKeys = preparedInsertRouteStatement.getGeneratedKeys();
            int routeId;
            if (generateRouteKeys.next()) {
                routeId = generateRouteKeys.getInt(1);
            } else throw new SQLException();
            //System.out.println('Запрос выполнен. Гуляй');

            route1 = new Route(
                    routeId,
                    route.getName(),
                    creationDate,
                    route.getCoordinates(),
                    route.getFrom(),
                    route.getTo(),
                    route.getDistance(),
                    user
            );
            dataBaseHandler.commit();
            return route1;

        } catch (SQLException sqlException) {
            System.out.println("Произошла ошибка при выполнении запроса на добавление нового объекта.");
            dataBaseHandler.rollback();
            throw new DataBaseException();
        } finally {
            dataBaseHandler.closePreparedStatement(preparedInsertCoordinatesStatement);
            dataBaseHandler.closePreparedStatement(preparedInsertRouteStatement);
            dataBaseHandler.closePreparedStatement(preparedInsertLocationToStatement);
            dataBaseHandler.closePreparedStatement(preparedInsertLocationFromStatement);
            dataBaseHandler.setNormalMode();
        }
    }


//TODO проверить соответствие и работоспособность

    /**
     * Класс, отвечающий за обновление элемента по его ид
     *
     * @param routeId
     * @param route
     * @throws DataBaseException
     * @throws SQLException
     */
    public void updateRouteId(int routeId, Route route) throws DataBaseException, SQLException {
        PreparedStatement preparedUpdateRouteByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByIdStatement = null;
        PreparedStatement preparedUpdateLocationFromStatement = null;
        PreparedStatement preparedUpdateLocationToStatement = null;
        try {
            dataBaseHandler.setCommitMode();
            dataBaseHandler.saveSQL();
            preparedUpdateRouteByIdStatement = dataBaseHandler.getPreparedStatement(UPDATE_ROUTE_BY_ID, false);
            preparedUpdateCoordinatesByIdStatement = dataBaseHandler.getPreparedStatement(UPDATE_COORDINATES_BY_ID, false);
            preparedUpdateLocationFromStatement = dataBaseHandler.getPreparedStatement(UPDATE_LOCATION_FROM_BY_ID, false);
            preparedUpdateLocationToStatement = dataBaseHandler.getPreparedStatement(UPDATE_LOCATION_TO_BY_ID, false);

            if (route.getName() != null) {
                preparedUpdateRouteByIdStatement.setString(1, route.getName());
                preparedUpdateRouteByIdStatement.setInt(2, routeId);
            }
            if (route.getDistance() > 1) {
                preparedUpdateRouteByIdStatement.setInt(1, route.getDistance());
                preparedUpdateRouteByIdStatement.setInt(2, routeId);
            }
            if (route.getCoordinates() != null) {
                preparedUpdateCoordinatesByIdStatement.setString(1, route.getCoordinates().toString());
                preparedUpdateCoordinatesByIdStatement.setInt(2, routeId);
            }
            if (route.getFrom() != null) {
                preparedUpdateLocationFromStatement.setString(1, route.getFrom().toString());
                preparedUpdateLocationFromStatement.setInt(2, routeId);
            }
            if (route.getTo() != null) {
                preparedUpdateLocationToStatement.setString(1, route.getTo().toString());
                preparedUpdateLocationFromStatement.setInt(2, routeId);
            }

            dataBaseHandler.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос UPDATE");
            dataBaseHandler.rollback();
            throw new DataBaseException();
        } finally {
            dataBaseHandler.closePreparedStatement(preparedUpdateLocationFromStatement);
            dataBaseHandler.closePreparedStatement(preparedUpdateLocationToStatement);
            dataBaseHandler.closePreparedStatement(preparedUpdateCoordinatesByIdStatement);
            dataBaseHandler.closePreparedStatement(preparedUpdateRouteByIdStatement);
            dataBaseHandler.setNormalMode();
        }
    }

    public boolean removeById(int routeId) throws DataBaseException, SQLException {
        PreparedStatement preparedRemoveRouteStatement = null;
        PreparedStatement preparedRemoveCoordinatesStatement = null;
        PreparedStatement preparedRemoveLocationFromStatement = null;
        PreparedStatement preparedRemoveLocationToStatement = null;
        try {
            preparedRemoveRouteStatement = dataBaseHandler.getPreparedStatement(REMOVE_ROUTE_BY_ID, false);
            preparedRemoveRouteStatement.setInt(1, getRouteById(routeId).getId());

            preparedRemoveCoordinatesStatement = dataBaseHandler.getPreparedStatement(REMOVE_COORDINATES_BY_ID, false);
            preparedRemoveRouteStatement.setInt(1, getRouteById(routeId).getId());

            preparedRemoveLocationFromStatement = dataBaseHandler.getPreparedStatement(REMOVE_LOCATION_FROM_BY_ID, false);
            preparedRemoveRouteStatement.setInt(1, getRouteById(routeId).getId());

            preparedRemoveLocationToStatement = dataBaseHandler.getPreparedStatement(REMOVE_LOCATION_TO_BY_ID, false);
            preparedRemoveRouteStatement.setInt(1, getRouteById(routeId).getId());
            if (preparedRemoveRouteStatement.executeUpdate() == 0
                    && preparedRemoveCoordinatesStatement.executeUpdate() == 0
                    && preparedRemoveLocationFromStatement.executeUpdate() == 0
                    && preparedRemoveLocationToStatement.executeUpdate() == 0)
                System.out.println("удалили элментик");
            return true;

        } catch (SQLException sqlException) {
            System.out.println("Произошла ошибка при выполнении запроса DELETE");
            throw new DataBaseException();
        } finally {
            dataBaseHandler.closePreparedStatement(preparedRemoveCoordinatesStatement);
            dataBaseHandler.closePreparedStatement(preparedRemoveRouteStatement);
            dataBaseHandler.closePreparedStatement(preparedRemoveLocationFromStatement);
            dataBaseHandler.closePreparedStatement(preparedRemoveLocationToStatement);
            return false;
        }
    }

    public void clearCollection() throws DataBaseException, SQLException {
        NavigableSet<Route> routes = getCollection();
        for (Route route : routes) {
            removeById(route.getId());
        }


    }
//    public RouteDAO getDAO() {
//        RouteInfo info = new RouteInfo();
//    }


    //YES
    @Override
    public void create(Route route) {
    }

    //NO
    @Override
    public boolean update(int id, RouteInfo routeInfo) {
        return false;
    }

    //NO
    @Override
    public boolean delete(int id) {
        try {
            return removeById(id);
        } catch (DataBaseException e) {

        } catch (SQLException e) {
        }
        return false;
    }

    //YES
    @Override
    public Deque<Route> getAll() {
        return null;
    }

    //NO
    @Override
    public void clear() {

    }
}