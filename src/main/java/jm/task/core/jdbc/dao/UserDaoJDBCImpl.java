package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();

    private static final String CREATE_SQL = """
            CREATE TABLE IF NOT EXISTS
            user (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(21),
                last_name VARCHAR(21),
                age TINYINT
                 )
            """;
    private static final String DROP_TABLE_SQL= """
            DROP TABLE IF EXISTS user
            """;
    private static final String SAVE_SQL = """
            INSERT INTO user (name, last_name, age)
            VALUES (?, ?, ?);
            """;
    private static final String DELETE_SQL = """
            DELETE FROM user
            WHERE id = ?
            """;
    private static final String GET_USERS_SQL = """
            SELECT *
            FROM user
            """;
    private static final String CLEAN_SQL = """
            TRUNCATE TABLE user
            """;

    private UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.get();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.get();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)){
            preparedStatement.setLong(1, id);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_USERS_SQL);
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.get();
             Statement statement = connection.createStatement()) {
            statement.execute(CLEAN_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }
}
