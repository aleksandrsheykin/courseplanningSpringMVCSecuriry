package main.controllers;

import main.models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 19.04.2017.
 */
@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showIndexPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/main");
        }
        return "login";
    }

}
