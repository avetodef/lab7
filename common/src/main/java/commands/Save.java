package commands;


import dao.RouteDAO;
import db.DataBaseDAO;
import db.DataBaseHandler;

import interaction.Response;
import interaction.Status;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс команды SAVE, предназначенный для сохранения элементов в коллекцию
 */
public class Save  {

    public void execute() {
            try {
                DataBaseHandler handler = new DataBaseHandler();
                try {
                    handler.saveSQL();
                    System.out.println("saved");
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + " save");
                e.printStackTrace();
            }
        //System.out.println("saved ");


        }
    }
