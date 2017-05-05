package main.services;

import main.models.dao.UserDao;
import main.models.dao.UserDaoImpl;
import main.models.pojo.User;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 21.04.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User auth(String login, String password) throws SQLException {
        User user = null;
        try {
            user = userDao.auth(login, password);
        } catch (SQLException e) {
            logger.error("SQLException in UserServiceImpl.auth()");
            throw e;
        }

        if (user == null) {
            return null;
        }

        return user;
    }

    public User registration(String mail, String password, String firstName, String lastName, Integer limit) throws SQLException {
        User user = null;
        try {
            user = userDao.insert(firstName, lastName, mail, password, limit);
        } catch (SQLException e) {
            logger.error("SQLException in UserServiceImpl.registration()");
            throw e;
        }
        return user;
    }

    public User getUserById(int id) throws SQLException {
        try {
            return userDao.getUser(id);
        } catch (SQLException e) {
            logger.error("SQLException in UserServiceImpl.getUserById()");
            throw e;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        try {
            return userDao.getAll();
        } catch (SQLException e) {
            logger.error("SQLException in UserServiceImpl.getAllUsers()");
            throw e;
        }
    }

    public boolean userExist(String mail) throws SQLException {
        try {
            return userDao.userExist(mail);
        } catch (SQLException e) {
            logger.error("SQLException in UserServiceImpl.getUserById()");
            throw e;
        }
    }

    public HttpServletRequest sendErrorAndParameters(HttpServletRequest req, String errorMsg, String errorInputs) {
        req.setAttribute("firstName", req.getParameter("firstName"));
        if (errorInputs.contains("firstName")) req.setAttribute("firstNameError", "1");

        req.setAttribute("lastName", req.getParameter("lastName"));
        if (errorInputs.contains("lastName")) req.setAttribute("lastNameError", "1");

        req.setAttribute("limit", req.getParameter("limit"));
        if (errorInputs.contains("limit")) req.setAttribute("limitError", "1");

        req.setAttribute("mail", req.getParameter("mail"));
        if (errorInputs.contains("mail")) req.setAttribute("mailError", "1");

        req.setAttribute("errorMsg", errorMsg);
        return req;
    }

}
