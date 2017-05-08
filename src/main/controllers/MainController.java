package main.controllers;

import main.models.pojo.User;
import main.services.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by admin on 19.04.2017.
 */
@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);
    private ErrorManager error;
    private PlanService planService;
    private ProductService productService;

    @Autowired
    public void setPlanService(PlanService planService) {
        this.planService = planService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView showMainPage(Model model) throws SQLException {
        ModelAndView mav = new ModelAndView();

        for (int replays=1; replays<=Options.REPLACE_COUNT; replays++) {
            try {
                model.addAttribute("planList", planService.getAllPlans());
                model.addAttribute("productList", productService.getAllProducts());
                break;
            } catch (SQLException e) {
                logger.error("SQLException in MainController.showMainPage()");
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

    @RequestMapping(value = "/mainadd", method = RequestMethod.POST)
    public ModelAndView mainAdd(@RequestParam(value = "date", required = true) Date date,
                                @RequestParam(value = "idProduct", required = true) Integer idProduct,
                                @RequestParam(value = "quantity", required = true) Integer quantity,
                                @RequestParam(value = "cost", required = true) Integer cost) {
        ModelAndView mav = new ModelAndView();

        try {
            planService.addPlan(date, idProduct, quantity, cost);
        } catch (SQLException e) {
            logger.error("SQLException in MainController.mainAdd()");
            error.setMsg("Oh sorry! Site crash, try again later");
            mav.addObject("error", error);
            mav.setViewName("error");
            return mav;
        }

        mav.setViewName("redirect:main");
        return mav;
    }

    @RequestMapping(value = "/maindel", method = RequestMethod.POST)
    public ModelAndView mainDel(@RequestParam(value = "idPlan") Integer idPlan) {
        logger.warn("DDDDD");
        ModelAndView mav = new ModelAndView();
        try {
            planService.deletePlanById(idPlan);
        } catch (SQLException e) {
            logger.error("SQLException in MainController.mainDel()");
            error.setMsg("Oh sorry! Site crash, try again later");
            mav.addObject("error", error);
            mav.setViewName("error");
            return mav;
        }

        mav.setViewName("redirect:main");
        return mav;
    }

    @RequestMapping(value = "/mainedit", method = RequestMethod.POST)
    public ModelAndView mainEdit(@RequestParam(value = "idPlan", required = true) Integer idPlan,
                                 @RequestParam(value = "date", required = true) Date date,
                                 @RequestParam(value = "idProduct", required = true) Integer idProduct,
                                 @RequestParam(value = "quantity", required = true) Integer quantity,
                                 @RequestParam(value = "cost", required = true) Integer cost) {
        logger.warn("DDDDD idPlan="+idPlan+" date="+date+" idProduct="+idProduct+" quantity="+quantity+" cost="+cost);
        ModelAndView mav = new ModelAndView();
        try {
            planService.updatePlan(idPlan, date, idProduct, quantity, cost);
        } catch (SQLException e) {
            logger.error("SQLException in MainController.mainDel()");
            error.setMsg("Oh sorry! Site crash, try again later");
            mav.addObject("error", error);
            mav.setViewName("error");
            return mav;
        }

        mav.setViewName("redirect:main");
        return mav;
    }

}
