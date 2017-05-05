package main.controllers;

import main.services.PlanService;
import main.services.PlanServiceImpl;
import main.services.UserService;
import main.services.UserServiceImpl;
import main.utils.ErrorManager;
import main.utils.Options;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * Created by admin on 22.04.2017.
 */
@Controller
public class AdminPanelController {
    private static Logger logger = Logger.getLogger(AdminPanelController.class);
    private UserService userService;
    private ErrorManager error;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView showAdminPage() {
        ModelAndView mav = new ModelAndView();

        for (int replays = 1; replays<= Options.REPLACE_COUNT; replays++)
            try {
                mav.addObject("userList", userService.getAllUsers());
                break;
            } catch (SQLException e) {
                logger.error("SQLException in AdminPanelController.showAdminPage()");
                if (replays == Options.REPLACE_COUNT) {
                    error.setMsg("Oh sorry! Site crash, try again later");
                    mav.addObject("error", error);
                    mav.setViewName("error");
                    return mav;
                }
            }

        mav.setViewName("adminPanel");
        return mav;
    }

}
