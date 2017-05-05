package main.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by admin on 22.04.2017.
 */
@Controller
@RequestMapping(value = "/logout")
public class LogoutController {
    private static Logger logger = Logger.getLogger(LogoutController.class);

    @RequestMapping(method = RequestMethod.GET)
    protected String logout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
