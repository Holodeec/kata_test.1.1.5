package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE if not exists users(id int primary key auto_increment," +
                    "name varchar(20) not null ,lastName varchar(20) not null ," +
                    "age int not null )");
            System.out.println("Создана новая таблица");
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу");
        }


    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("drop table if exists users");
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pps = Util.getConnection().prepareStatement("insert into users values (id,?,?,?)")) {
            pps.setString(1, name);
            pps.setString(2, lastName);
            pps.setByte(3, age);
            pps.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("User с именем - " + name + " не добавлен в базу данных");
        }

    }


    public void removeUserById(long id) {
        try (PreparedStatement pps = Util.getConnection().prepareStatement("DELETE FROM users WHERE Id =?")) {
            pps.setLong(1,id);
            pps.executeUpdate();
            System.out.println("User c id " + id +" удален");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            System.out.println("Получилось");
        } catch (SQLException e) {
            System.out.println("Не удалось получить Users");
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE users");
            System.out.println("Таблица успешно очищена");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
        }

    }
}
