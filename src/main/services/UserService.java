package main.services;

import main.models.pojo.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 21.04.2017.
 */
public interface UserService {

    User auth(String login, String password) throws SQLException;
    User registration(String mail, String password, String firstName, String lastName, Integer limit) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    User getUserById(int id) throws SQLException;
    boolean userExist(String mail) throws SQLException;
    HttpServletRequest sendErrorAndParameters(HttpServletRequest req, String errorMsg, String errorInputs);
}
