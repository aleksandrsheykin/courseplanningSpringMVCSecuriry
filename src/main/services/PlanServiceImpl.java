package main.services;

import main.models.dao.PlanDao;
import main.models.dao.PlanDaoImpl;
import main.models.dao.UserDaoImpl;
import main.models.pojo.Plan;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 19.04.2017.
 */
@Service
public class PlanServiceImpl implements PlanService {
    private static Logger logger = Logger.getLogger(PlanServiceImpl.class);
    private PlanDao planDao;

    @Autowired
    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }

    public List<Plan> getAllPlans() throws SQLException {
        try {
            return planDao.getAll(true);
        } catch (SQLException e) {
            logger.warn("SQLException in PlanServiceImpl.getAllPlans()");
            throw e;
        }
    }

    public boolean deletePlanById(Integer id) throws SQLException {
        try {
            return planDao.delete(id);
        } catch (SQLException e) {
            logger.warn("SQLException in PlanServiceImpl");
            throw e;
        }
    }
}
