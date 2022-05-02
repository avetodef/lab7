package utils;

import db.DataBaseDAO;

public class IdGenerator {
    private static int id;
    public static void reloadId(DataBaseDAO dao) {
        if(dao.getAll() != null)
            id = dao.getAll().getLast().getId();
        else
            id = 1;
    }
    public static int nextId(){
        return ++id;
    }
}
