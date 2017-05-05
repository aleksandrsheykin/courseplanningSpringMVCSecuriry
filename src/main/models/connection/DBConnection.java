package main.models.connection;


import main.controllers.LoginController;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by admin on 19.04.2017.
 */
public class DBConnection {

    private static DataSource datasource = new DataSource();
    static {
        initPool();
    }

    private static Logger logger = Logger.getLogger(DBConnection.class);

    public static Connection initConnection() {
        java.sql.Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/shopping_planning", "postgres", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void initPool() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:postgresql://localhost/shopping_planning");
        p.setDriverClassName("org.postgresql.Driver");
        p.setUsername("postgres");
        p.setPassword("1234");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        datasource.setPoolProperties(p);
    }

    public static Connection getConnection() throws SQLException {
        try {
            return datasource.getConnection();
        } catch (SQLException e) {
            logger.error("SQLException in DBConnection.getConnection()");
            throw e;
        }
    }
}
