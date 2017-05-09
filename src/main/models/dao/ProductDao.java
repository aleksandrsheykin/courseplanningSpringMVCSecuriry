package main.models.dao;

import main.models.pojo.Product;
import main.models.pojo.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 19.04.2017.
 */
public interface ProductDao {
    List<Product> getAll() throws SQLException;
    Product get(int id);
    boolean update(Product product) throws SQLException;
    boolean update(Integer id, String name, String desc) throws SQLException;
    boolean delete(Product product) throws SQLException;
    boolean delete(Integer id) throws SQLException;
    boolean insert(Product product);
    boolean insert(String name, String desc) throws SQLException;
    boolean getDelResolution(Integer idProduct) throws SQLException;
}