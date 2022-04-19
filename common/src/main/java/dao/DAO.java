package dao;


import utils.Route;
import utils.RouteInfo;

import java.util.Deque;

public interface DAO {
    void create(Route route);
    boolean update(int id, RouteInfo routeInfo);
    boolean delete(int id);
    Deque<Route> getAll();
    void clear();
    //Map<String, String> getDescription();
}
