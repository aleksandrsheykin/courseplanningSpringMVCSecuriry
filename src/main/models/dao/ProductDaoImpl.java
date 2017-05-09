package main.models.dao;

import main.controllers.listeners.NewAppStartListener;
import main.models.connection.DBConnection;
import main.models.pojo.Product;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
public class ProductDaoImpl implements ProductDao {

    private static Logger logger = Logger.getLogger(ProductDaoImpl.class);

    public List<Product> getAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *"+
                    " from products ORDER BY product_id");

            ResultSet result = preparedStatement.executeQuery();

            List<Product> listProduct = new ArrayList<Product>();
            while (result.next()) {
                listProduct.add(new Product(
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("product_description"),
                        result.getInt("product_user_id"))

                );
            }
            preparedStatement.close();
            result.close();
            return listProduct;

        } catch (SQLException e) {
            logger.warn("SQLException in Product.getAll()");
            throw e;
        }
    }

    public Product get(int id) {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *"+
                    " from products where product_id=?");
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            result.next();
            return new Product(
                    result.getInt("product_id_"),
                    result.getString("product_name"),
                    result.getString("product_description"),
                    result.getInt("product_user_id")
            );

        } catch (SQLException e) {
            logger.warn("SQLException in Product.get()");
            return null;
        }
    }

    public boolean update(Product product) throws SQLException {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET(" +
                    " product_name, product_description, product_user_id)" +
                    " = (?, ?, ?) WHERE product_id = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getUser_id());
            preparedStatement.setInt(4, product.getIdProduct());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Product.update()");
            throw e;
        }
    }

    public boolean update(Integer id, String name, String desc) throws SQLException {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET(" +
                    " product_name, product_description, product_user_id)" +
                    " = (?, ?, ?) WHERE product_id = ? RETURNING product_id");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, id);
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Product.update()");
            throw e;
        }
    }

    public boolean delete(Product product) throws SQLException {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from products " +
                    " WHERE product_id = ? RETURNING product_id");
            preparedStatement.setInt(1, product.getIdProduct());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Product.delete()");
            throw e;
        }
    }

    public boolean delete(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from products " +
                    " WHERE product_id = ? RETURNING product_id");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Product.delete() id="+id);
            throw e;
        }
    }

    public boolean insert(Product product) {
        try {
            Connection connection = DBConnection.initConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into products (" +
                    " product_id, product_name, product_description, product_user_id)" +
                    " values (?, ?, ?, ?)");
            preparedStatement.setInt(1, product.getIdProduct());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getUser_id());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Product.insert()");
            return false;
        }
    }

    public boolean insert(String name, String desc) throws SQLException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into products " +
                    " (product_name, product_description, product_user_id)" +
                    " values (?, ?, ?) RETURNING product_id");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Product.insert() Signature: name="+name+" desc="+desc);
            throw e;
        }
    }

    @Override
    public boolean getDelResolution(Integer idProduct) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count"+
                    " from plans where plan_product_id = ?");
            preparedStatement.setInt(1, idProduct);

            ResultSet result = preparedStatement.executeQuery();

            Boolean r = false;
            if (result.next()) {
                r = result.getInt("count") > 0 ? false : true;
            }

            preparedStatement.close();
            result.close();
            return r;

        } catch (SQLException e) {
            logger.warn("SQLException in Product.getDelResolution() idProduct="+idProduct);
            throw e;
        }
    }
}
