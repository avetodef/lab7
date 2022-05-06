package dao;

import console.ConsoleOutputer;
import interaction.User;
import utils.Route;
import utils.RouteInfo;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class DataBaseDAO implements DAO {

    static final String url = "jdbc:postgresql://localhost:5432/postgres";
    static final String username = "postgres";
    static final String password = "lterm54201";
    private final static ConsoleOutputer o = new ConsoleOutputer();
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (conn != null)
            o.printWhite("подключено к датабейс");
        else
            o.printRed("не удается подключиться к датабазе");

        return conn;
    }

    @Override
    public void create(Route route) {
        Connection connection = connect();
        String statement = "INSERT INTO collection( name, coordX, coordY, creationdate, " +
                "fromX, fromY, fromName, toX, toY, nameTo , distance, userID)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(statement);
            pstmt.setString(1, route.getName());
            pstmt.setDouble(2, route.getCoordinates().getCoorX());
            pstmt.setDouble(3, route.getCoordinates().getCoorY());
            pstmt.setTimestamp(4, Timestamp.valueOf(route.getCreationDate()));
            pstmt.setDouble(5, route.getFrom().getFromX());
            pstmt.setLong(6, route.getFrom().getFromY());
            pstmt.setString(7, route.getFrom().getName());
            pstmt.setInt(8, route.getTo().getToX());
            pstmt.setFloat(9, route.getTo().getToY());
            pstmt.setString(10, route.getTo().getName());
            pstmt.setInt(11, route.getDistance());
            pstmt.setInt(12, route.getUser().getId());

            pstmt.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean update(int id, RouteInfo routeInfo, Route route) {
        Connection connection = connect();
        String statement = "INSERT INTO collection( name, coordX, coordY, creationdate, " +
                "fromX, fromY, fromName, toX, toY, nameTo , distance, userID)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(statement);
            pstmt.setString(1, routeInfo.name);
            pstmt.setDouble(2, routeInfo.x);
            pstmt.setDouble(3, routeInfo.y);
            pstmt.setTimestamp(4, Timestamp.valueOf(String.valueOf(routeInfo.creationDate))); //TODO мб упадет
            pstmt.setDouble(5, routeInfo.fromX);
            pstmt.setLong(6, routeInfo.fromY);
            pstmt.setString(7, routeInfo.nameFrom);
            pstmt.setInt(8, routeInfo.toX);
            pstmt.setFloat(9, routeInfo.toY);
            pstmt.setString(10, routeInfo.nameTo);
            pstmt.setInt(11, route.getDistance());

            if (pstmt.execute())
                return true;
            else return false;
        } catch (SQLException w) {
            w.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id, Route route) {
        Connection connection = connect();
        String SQL = "DELETE FROM collection WHERE id = ? AND user = ?";
        int userID = route.getUser().getId();
        try {
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setInt(1, id);
            pstmt.setInt(2, userID);

            if (pstmt.executeUpdate() != 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Deque<Route> getAll() {
        Connection connection = connect();
        String SQL = "SELECT id, name, coordX, coordY, creationdate," +
                "fromX, fromY, fromName, toX, toY, nameTo , distance, userID FROM collection";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            //TODO наверное не сработает
            return (Deque<Route>) rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayDeque<Route>();
        } catch (NullPointerException e) {
            System.out.println("пусто в душе и в коллеекции ");
            return new ArrayDeque<Route>();
        }
    }

    @Override
    public void clear() {
        Connection connection = connect();
        String SQL = "DELETE FROM collection ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeFirst(RouteDAO dao) {
        Connection connection = connect();
        String sql = "DELETE FROM collection WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            int id = dao.getAll().getFirst().getId();

            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RouteDAO getDAO() {
        try {
            Connection connection = connect();
            Deque<Route> routes = getAll();
            RouteDAO dao = new RouteDAO();
            if (routes.isEmpty()) return new RouteDAO();
            else {
                for (Route route : routes) {
                    dao.create(route);
                }
                return dao;
            }
        } catch (RuntimeException e) {
        }
        return new RouteDAO();
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO users(username, password) VALUES (?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUsername(String username) {
        String sql = "SELECT username FROM users WHERE username=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkPassword(String password) {
        String sql = "SELECT password FROM users WHERE password=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, password);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkID(int id) {
        String sql = "SELECT user_id FROM users WHERE user_id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public String getusername(int id) {
        String SQL = "SELECT username FROM users WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setInt(1, id);

            return pstmt.executeQuery().getObject(id, String.class);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

            return null;
        }
    }

    public String getPassword(int id) {
        String SQL = "SELECT password FROM users WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            pstmt.setInt(1, id);
            String rs = pstmt.executeQuery().getString(id);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int getUserID(String username) {
        String sql = "SELECT user_id FROM users WHERE username=?";
        int id = 1;
        try {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setString(1, username);

            ResultSet rs = prstmt.executeQuery();
            id = rs.getInt(username);
            return id;

        } catch (SQLException e) {

        }
        return ++id;
    }

}
