package main.controllers;

import main.models.pojo.User;
import main.services.PlanService;
import main.services.PlanServiceImpl;
import main.services.UserService;
import main.services.UserServiceImpl;
import main.utils.ErrorManager;
import main.utils.Options;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by admin on 19.04.2017.
 */
@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);
    private ErrorManager error;
    private PlanService planService;

    @Autowired
    public void setPlanService(PlanService planService) {
        this.planService = planService;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView showLoginPage(Model model) throws SQLException {
        ModelAndView mav = new ModelAndView();

        for (int replays=1; replays<=Options.REPLACE_COUNT; replays++) {
            try {
                model.addAttribute("planList", planService.getAllPlans());
                break;
            } catch (SQLException e) {
                logger.error("SQLException in LoginController.registration()");
                if (replays == Options.REPLACE_COUNT) {
                    error.setMsg("Oh sorry! Site crash, try again later");
                    mav.addObject("error", error);
                    mav.setViewName("error");
                    return mav;
                }
            }
        }
        mav.setViewName("main");
        return mav;
    }

/*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer deleteId = Integer.parseInt(req.getParameter("deleteId"));
        if (deleteId != null && deleteId > 0) {
            planService.deletePlanById(deleteId);
        }
        resp.sendRedirect(req.getContextPath() + "/main");
    }*/

}
