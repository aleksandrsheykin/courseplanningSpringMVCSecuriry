package main.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 27.04.2017.
 */
@RequestMapping("**")
@ResponseBody
public class Error404Controller {

    public String fallbackMethod(){
        return "error404";
    }
}
