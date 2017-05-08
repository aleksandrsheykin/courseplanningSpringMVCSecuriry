package main.models.dao;

import main.models.pojo.Plan;
import main.models.pojo.Product;
import main.models.pojo.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.models.connection.DBConnection;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 19.04.2017.
 */
@Repository
public class PlanDaoImpl implements PlanDao {

    private static Logger logger = Logger.getLogger(PlanDaoImpl.class);

    public boolean delete(Integer id) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            logger.error("SQLException in PlanDaoImpl.delete()");
            throw e;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from plans " +
                    " WHERE plan_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.delete()");
            return false;
        }
    }

    public List<Plan> getAll(boolean joinProduct) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            logger.error("SQLException in PlanDaoImpl.getAll()");
            throw e;
        }
        try {
            String sqlText;
            if (joinProduct) {
                sqlText = "select * from plans pl left join products pr on pr.product_id = pl.plan_product_id";
            } else {
                sqlText = "select * from plans";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sqlText);
            ResultSet result = preparedStatement.executeQuery();

            List<Plan> listPlan = new ArrayList<Plan>();

            while (result.next()) {
                listPlan.add(new Plan(
                        result.getInt("plan_id"),
                        result.getDate("plan_data"),
                        result.getInt("plan_quantity"),
                        result.getInt("plan_cost"),
                        result.getInt("plan_user_id"),
                        new Product(result.getInt("product_id"),
                                result.getString("product_name"),
                                result.getString("product_description"),
                                result.getInt("product_user_id")))
                );
            }

            preparedStatement.close();
            result.close();
            return listPlan;

        } catch (SQLException e) {
            logger.warn("SQLException in Plan.getAll()");
            return null;
        }
    }

    public Plan get(int id) {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *"+
                    " from plans pl left join products pr on pr.product_id = pl.plan_product_id where plan_id=?");
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            result.next();
            return new Plan(
                    result.getInt("plan_id"),
                    result.getDate("plan_data"),
                    result.getInt("plan_quantity"),
                    result.getInt("plan_cost"),
                    result.getInt("plan_user_id"),
                    new Product(result.getInt("product_id"),
                            result.getString("product_name"),
                            result.getString("product_description"),
                            result.getInt("product_user_id")));

        } catch (SQLException e) {
            logger.warn("SQLException in Plan.get()");
            return null;
        }
    }

    public boolean update(Plan plan) {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE plans SET(" +
                    " plan_data, plan_quantity, plan_cost, plan_user_id, plan_product_id)" +
                    " = (?, ?, ?, ?, ?) WHERE plan_id = ?");
            preparedStatement.setDate(1, (Date) plan.getDatePlan());
            preparedStatement.setInt(2, plan.getQuantity());
            preparedStatement.setInt(3, plan.getCost());
            preparedStatement.setInt(4, plan.getUserId());
            preparedStatement.setInt(5, plan.getProduct().getIdProduct());
            preparedStatement.setInt(6, plan.getId_plan());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.update()");
            return false;
        }
    }

    public boolean delete(Plan plan) {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from plans " +
                    " WHERE plan_id = ?");
            preparedStatement.setInt(1, plan.getId_plan());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.delete()");
            return false;
        }
    }

    public boolean insert(Plan plan) {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into plans (" +
                    " plan_id, plan_data, plan_quantity, plan_cost, plan_user_id, plan_product_id)" +
                    " values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, plan.getId_plan());
            preparedStatement.setDate(2, (Date) plan.getDatePlan());
            preparedStatement.setInt(3, plan.getQuantity());
            preparedStatement.setInt(4, plan.getCost());
            preparedStatement.setInt(5, plan.getUserId());
            preparedStatement.setInt(6, plan.getProduct().getIdProduct());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.insert()");
            return false;
        }
    }

    @Override
    public void insert(Date date, Integer idProduct, Integer quantity, Integer cost) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into plans (" +
                    " plan_data, plan_quantity, plan_cost, plan_user_id, plan_product_id)" +
                    " values (?, ?, ?, ?, ?) RETURNING plan_id");
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, cost);
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, idProduct);
            preparedStatement.executeQuery();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.insert()");
            throw e;
        }
    }

    @Override
    public void updatePlan(Integer idPlan, Date date, Integer idProduct, Integer quantity, Integer cost) throws SQLException {
        Connection connection = DBConnection.initConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE plans SET(" +
                    " plan_data, plan_quantity, plan_cost, plan_user_id, plan_product_id)" +
                    " = (?, ?, ?, ?, ?) WHERE plan_id = ? RETURNING plan_id");
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, cost);
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, idProduct);
            preparedStatement.setInt(6, idPlan);
            preparedStatement.executeQuery();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.updatePlan()");
            throw e;
        }
    }
}

