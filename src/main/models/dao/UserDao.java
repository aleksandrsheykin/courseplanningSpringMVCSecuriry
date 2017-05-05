package main.models.dao;

import main.models.pojo.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 19.04.2017.
 */
public interface UserDao {
    List<User> getAll() throws SQLException;
    User getUser(int id) throws SQLException;
    User getUser(String mail) throws SQLException;
    boolean update(User user) throws SQLException;
    boolean userExist(String mail) throws SQLException;
    boolean delete(User user) throws SQLException;
    boolean insert(String firsName, String lastName, String mail, String password, Integer limit, boolean isAdmin, Integer idUser, boolean isBlocked) throws SQLException;
    User insert(String firsName, String lastName, String mail, String password, Integer limit) throws SQLException;
    User findUserByLoginAndPassword(String login, String password) throws SQLException;
    User auth(String login, String password) throws SQLException;
}