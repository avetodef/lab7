package db;//import common.exceptions.DatabaseHandlingException;
//import common.interaction.Use;

import interaction.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUsersDolboeb {

    /**
     * A manager of user database.
     */
    public class DatabaseUserManager {
        // USER_TABLE
        private final String SELECT_USER_BY_ID = "SELECT * FROM " + DataBaseHandler.USER_TABLE +
                " WHERE " + DataBaseHandler.USER_TABLE_ID_COLUMN + " = ?";
        private final String SELECT_USER_BY_LOGIN = "SELECT * FROM " + DataBaseHandler.USER_TABLE +
                " WHERE " + DataBaseHandler.USER_TABLE_LOGIN_COLUMN + " = ?";
        private final String SELECT_USER_BY_LOGIN_AND_PASSWORD = SELECT_USER_BY_LOGIN + " AND " +
                DataBaseHandler.USER_TABLE_PASSWORD_COLUMN + " = ?";
        private final String INSERT_USER = "INSERT INTO " +
                DataBaseHandler.USER_TABLE + " (" +
                DataBaseHandler.USER_TABLE_LOGIN_COLUMN + ", " +
                DataBaseHandler.USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?)";

        private DataBaseHandler databaseHandler;

        public DatabaseUserManager(DataBaseHandler databaseHandler) {
            this.databaseHandler = databaseHandler;
        }

        /**
         * @param userId User id.
         * @return User by id.
         * @throws SQLException When there's exception inside.
         */
        public User getUserById(int userId) throws SQLException {
            User user;
            PreparedStatement preparedSelectUserByIdStatement = null;
            try {
                preparedSelectUserByIdStatement =
                        databaseHandler.getPreparedStatement(SELECT_USER_BY_ID, false);
                preparedSelectUserByIdStatement.setLong(1, userId);
                ResultSet resultSet = preparedSelectUserByIdStatement.executeQuery();
                System.out.println("Выполнен запрос SELECT_USER_BY_ID.");
                //ServerApp.logger.info("Выполнен запрос SELECT_USER_BY_ID.");
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getString(DataBaseHandler.USER_TABLE_PASSWORD_COLUMN),
                            resultSet.getString(DataBaseHandler.USER_TABLE_PASSWORD_COLUMN)
                    );
                } else throw new SQLException();
            } catch (SQLException exception) {
                System.err.println("Произошла ошибка при выполнении запроса SELECT_USER_BY_ID!");
                //ServerApp.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_ID!");
                throw new SQLException(exception);
            } finally {
                databaseHandler.closePreparedStatement(preparedSelectUserByIdStatement);
            }
            return user;
        }

        /**
         * Check user by username and password.
         *
         * @param user User.
         * @return Result set.
         */
        //throws DatabaseHandlingException
        public boolean checkUserByUsernameAndPassword(User user) {
            PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
            try {
                preparedSelectUserByUsernameAndPasswordStatement =
                        databaseHandler.getPreparedStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD, false);
                preparedSelectUserByUsernameAndPasswordStatement.setString(1, user.getUsername());
                preparedSelectUserByUsernameAndPasswordStatement.setString(2, user.getPassword());
                ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
                //ServerApp.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
                System.out.println("Выполнен запрос SELECT_USER_BY_USERNAME_AND_PASSWORD.");
                return resultSet.next();
            } catch (SQLException exception) {
                System.err.println("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
                //ServerApp.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
                //throw new DatabaseHandlingException();
            } finally {
                databaseHandler.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
            }
            return false;
        }

        /**
         * Get user id by username.
         *
         * @param user User.
         * @return User id.
         */
        public long getUserIdByUsername(User user) {
            int userId = 0;
            PreparedStatement preparedSelectUserByUsernameStatement = null;
            try {
                preparedSelectUserByUsernameStatement =
                        databaseHandler.getPreparedStatement(SELECT_USER_BY_LOGIN, false);
                preparedSelectUserByUsernameStatement.setString(1, user.getUsername());
                ResultSet resultSet = preparedSelectUserByUsernameStatement.executeQuery();
                System.out.println("Выполнен запрос SELECT_USER_BY_USERNAME.");
                //ServerApp.logger.info("Выполнен запрос SELECT_USER_BY_USERNAME.");
                if (resultSet.next()) {
                    userId = resultSet.getInt(DataBaseHandler.USER_TABLE_ID_COLUMN);
                } else userId = -1;
                return userId;
            } catch (SQLException exception) {
                System.err.println("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME!");
                //ServerApp.logger.error("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME!");
                //throw new DatabaseHandlingException();
            } finally {
                databaseHandler.closePreparedStatement(preparedSelectUserByUsernameStatement);
            }
            return userId;
        }

        /**
         * Insert user.
         *
         * @param user User.
         * @return Status of insert.
         */
        public boolean insertUser(User user)  {
            PreparedStatement preparedInsertUserStatement = null;
            try {
                if (getUserIdByUsername(user) != -1) return false;
                preparedInsertUserStatement =
                        databaseHandler.getPreparedStatement(INSERT_USER, false);
                preparedInsertUserStatement.setString(1, user.getUsername());
                preparedInsertUserStatement.setString(2, user.getPassword());
                if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
                System.out.println("Выполнен запрос INSERT_USER.");
                //ServerApp.logger.info("Выполнен запрос INSERT_USER.");
                return true;
            } catch (SQLException exception) {
                System.err.println("Произошла ошибка при выполнении запроса INSERT_USER!");
                //ServerApp.logger.error("Произошла ошибка при выполнении запроса INSERT_USER!");
                //throw new DatabaseHandlingException();
            } finally {
                databaseHandler.closePreparedStatement(preparedInsertUserStatement);
            }
            return false;
        }
    }
}