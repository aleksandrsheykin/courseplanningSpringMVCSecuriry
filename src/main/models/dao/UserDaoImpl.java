package main.models.dao;

import main.models.connection.DBConnection;
import main.models.pojo.User;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 19.04.2017.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    public List<User> getAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *"+
                    " from users");

            ResultSet result = preparedStatement.executeQuery();

            List<User> listUser = new ArrayList<User>();
            while (result.next()) {
                listUser.add(new User(
                        result.getInt("user_id"),
                        result.getString("user_firstName"),
                        result.getString("user_lastName"),
                        result.getString("user_mail"),
                        result.getString("user_password"),
                        result.getInt("user_limit"),
                        result.getBoolean("user_is_admin"),
                        result.getBoolean("user_is_blocked")
                        )
                );
            }
            preparedStatement.close();
            result.close();
            return listUser;

        } catch (SQLException e) {
            logger.warn("SQLException in User.getAll()");
            throw e;
        }
    }

    public User getUser(int id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *"+
                    " from users where user_id=?");
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            result.next();
            User user = new User(
                    result.getInt("user_id"),
                    result.getString("user_firstName"),
                    result.getString("user_lastName"),
                    result.getString("user_mail"),
                    result.getString("user_password"),
                    result.getInt("user_limit"),
                    result.getBoolean("user_is_admin"),
                    result.getBoolean("user_is_blocked"));
            preparedStatement.close();
            result.close();
            return user;

        } catch (SQLException e) {
            logger.warn("SQLException in User.get()");
            throw e;
        }
    }

    public User getUser(String mail) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *"+
                    " from users where upper(user_mail) = upper(?)");
            preparedStatement.setString(1, mail);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                User user = new User(
                        result.getInt("user_id"),
                        result.getString("user_firstName"),
                        result.getString("user_lastName"),
                        result.getString("user_mail"),
                        result.getString("user_password"),
                        result.getInt("user_limit"),
                        result.getBoolean("user_is_admin"),
                        result.getBoolean("user_is_blocked"));
                preparedStatement.close();
                result.close();
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            logger.warn("SQLException in User.get()");
            throw e;
        }
    }

    public boolean update(User user) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET(" +
                    " user_firstName, user_lastName, user_mail, user_password, user_limit, user_is_admin, user_id_blocked)" +
                    " = (?, ?, ?, ?, ?, ?, ?) WHERE id = ?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getLimit());
            preparedStatement.setBoolean(6, user.getIsAdmin());
            preparedStatement.setBoolean(7, user.getIsBlocked());
            preparedStatement.setInt(8, user.getIdUser());
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in User.update()");
            throw e;
        }
    }

    public boolean delete(User user) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            logger.warn("SQLException");
            throw e;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from users " +
                    " WHERE id = ?");
            preparedStatement.setInt(1, user.getIdUser());
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in User.delete()");
            return false;
        }
    }

    public boolean insert(String firsName, String lastName, String mail, String password, Integer limit, boolean isAdmin, Integer idUser, boolean isBlocked) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert users (" +
                    " user_firstName, user_lastName, user_mail, user_password, user_limit, user_is_admin, user_id, isBlocked)" +
                    " = (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, firsName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, mail);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, limit);
            preparedStatement.setBoolean(6, isAdmin);
            preparedStatement.setInt(7, idUser);
            preparedStatement.setBoolean(8, isBlocked);
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in User.insert()");
            throw e;
        }
    }

    public User insert(String firsName, String lastName, String mail, String password, Integer limit) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (" +
                    " user_firstname, user_lastname, user_mail, user_password, user_limit, user_is_admin, user_is_blocked)" +
                    " values (?, ?, ?, ?, ?, ?, ?) RETURNING user_id, " +
                    " user_firstname, user_lastname, user_mail, user_password, user_limit, user_is_admin, user_is_blocked");
            preparedStatement.setString(1, firsName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, mail);
            preparedStatement.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));
            preparedStatement.setInt(5, limit);
            preparedStatement.setBoolean(6, false);
            preparedStatement.setBoolean(7, false);

            ResultSet result = preparedStatement.executeQuery();
            result.next();
            User user = new User(
                    result.getInt("user_id"),
                    result.getString("user_firstName"),
                    result.getString("user_lastName"),
                    result.getString("user_mail"),
                    result.getString("user_password"),
                    result.getInt("user_limit"),
                    result.getBoolean("user_is_admin"),
                    result.getBoolean("user_is_blocked"));
            preparedStatement.close();
            result.close();
            return user;
        } catch (SQLException e) {
            logger.warn("SQLException in User.insert()");
            throw e;
        }
    }

    public User auth(String login, String password) throws SQLException {
        User user = getUser(login);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean userExist(String mail) throws SQLException {
        return getUser(mail)!=null?true:false;
    }

    public User findUserByLoginAndPassword(String login, String password) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from users where user_mail = ? and user_password = ? and user_is_blocked is false");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                User user = new User(
                        result.getInt("user_id"),
                        result.getString("user_firstName"),
                        result.getString("user_lastName"),
                        result.getString("user_mail"),
                        result.getString("user_password"),
                        result.getInt("user_limit"),
                        result.getBoolean("user_is_admin"),
                        result.getBoolean("user_is_blocked"));
                preparedStatement.close();
                result.close();
                return user;
            }
            return null;
        } catch (SQLException e) {
            logger.warn("SQLException in User.findUserByLoginAndPassword()");
            throw e;
        }
    }
}
