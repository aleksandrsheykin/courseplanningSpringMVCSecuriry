package main.test;

import main.models.dao.UserDao;
import main.models.dao.UserDaoImpl;
import main.models.pojo.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * Created by admin on 27.04.2017.
 */
public class UserDaoTest {

    private static UserDao userDaoEthalon;
    private static User userEthalon;

    @BeforeClass
    public static void init() {
        userDaoEthalon = new UserDaoImpl();
    }

    @Test
    public void TestDaoUser() {
        try {
            userEthalon = userDaoEthalon.insert("test", "test", "ttt@ttt.ru", "123", 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue("test".equals(userEthalon.getFirstName()));
        assertTrue("test".equals(userEthalon.getLastName()));
        assertTrue(userEthalon.getLimit() == 1);
        try {
            userDaoEthalon.delete(userEthalon);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
