package main.controllers;

import main.models.pojo.User;
import main.services.UserService;
import main.services.UserServiceImpl;
import main.utils.ErrorManager;
import main.utils.Options;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@SessionAttributes("user")
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    private UserService userService;
    private ErrorManager error = new ErrorManager("");

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/main");
        }

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "login", required = true) String login,
                              @RequestParam(value = "password", required = true) String password,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        ModelAndView mav = new ModelAndView();

        User user = null;
        for (int replays=1; replays<=Options.REPLACE_COUNT; replays++)
            try {
                user = userService.auth(login, password);
                break;
            } catch (SQLException e) {
                logger.error("SQLException in LoginController.registration()");
                if (replays == Options.REPLACE_COUNT) {
                    error.setMsg("Oh sorry! Registration error, try again later");
                    mav.addObject("error", error);
                    mav.setViewName("error");
                    return mav;
                }
            }

        if (user != null) {
            model.addAttribute("user", user);
            mav.setViewName("redirect:main");
        } else {
            error.setMsg("Failed login");
            mav.addObject("error", error);
            mav.setViewName("login");
        }
        return mav;
    }

/*    @ModelAttribute(value = "error") ???
    public ErrorManager addError() {
        return error;
    }*/
}
