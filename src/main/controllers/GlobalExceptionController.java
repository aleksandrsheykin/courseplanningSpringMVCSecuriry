package main.controllers;

import main.utils.ErrorManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

/**
 * Created by admin on 09.05.2017.
 */
@ControllerAdvice
public class GlobalExceptionController {
    private ErrorManager error = new ErrorManager("");
    private static Logger logger = Logger.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException e) {
        logger.error("error: "+e.getMessage());
        ModelAndView mav = new ModelAndView("error");
        error.setMsg("Oh sorry! Site crash, try again later");
        mav.addObject("error", error);
        return mav;
    }

/*    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="error404")
    @ExceptionHandler
    public String showError404() {
        return "error404";
    }*/
}
