package main.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import main.models.pojo.User;
import main.services.*;
import main.utils.ErrorManager;
import main.utils.Options;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by admin on 26.04.2017.
 */
@Controller
public class ProductController {

    private ProductService productService;
    private static Logger logger = Logger.getLogger(MainController.class);
    private ErrorManager error = new ErrorManager("");

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView showProductPage(Model model) throws SQLException {
        ModelAndView mav = new ModelAndView();

        model.addAttribute("productList", productService.getAllProducts());
        mav.setViewName("products");
        return mav;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ModelAndView requestManager(@RequestParam(value = "action", required = true) String action,
                                       @RequestParam(value = "id", required = false) Integer id,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "description", required = false) String desc,
                                       Model model) throws SQLException {
        ModelAndView mav = new ModelAndView();

        if (action.equals("add")) {
            productService.addProduct(name, desc);
        } else if (action.equals("edit")) {
            productService.editProduct(id, name, desc);
        } else if (action.equals("delete")) {
            if (productService.getDelResolution(id)) {
                productService.deleteProduct(id);
            } else {
                error.setMsg("There are plans with this product, delete them");
                model.addAttribute("productList", productService.getAllProducts());
                model.addAttribute("error", error);
                mav.setViewName("products");
                return mav;
            }
        }

        mav.setViewName("redirect:products");
        return mav;
    }

}
