package main.models.dao;

import main.models.pojo.Plan;
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
                        result.getInt("plan_product_id"))
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
                    " from plans where plan_id=?");
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            result.next();
            return new Plan(
                    result.getInt("plan_id"),
                    result.getDate("plan_data"),
                    result.getInt("plan_quantity"),
                    result.getInt("plan_cost"),
                    result.getInt("plan_user_id"),
                    result.getInt("plan_product_id")
            );

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
            preparedStatement.setInt(5, plan.getProductId());
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
            PreparedStatement preparedStatement = connection.prepareStatement("insert plans (" +
                    " plan_id, plan_data, plan_quantity, plan_cost, plan_user_id, plan_product_id)" +
                    " = (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, plan.getId_plan());
            preparedStatement.setDate(2, (Date) plan.getDatePlan());
            preparedStatement.setInt(3, plan.getQuantity());
            preparedStatement.setInt(4, plan.getCost());
            preparedStatement.setInt(5, plan.getUserId());
            preparedStatement.setInt(6, plan.getProductId());
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            logger.warn("SQLException in Plan.insert()");
            return false;
        }
    }
}

