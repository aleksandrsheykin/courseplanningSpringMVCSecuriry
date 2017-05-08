package main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 27.04.2017.
 */
@Controller
public class Error403Controller {

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String showPage(){
        return "error403";
    }
}
