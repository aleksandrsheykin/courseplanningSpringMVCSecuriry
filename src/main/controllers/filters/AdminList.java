package main.controllers.filters;

import main.models.pojo.User;
import main.services.UserService;
import main.services.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 22.04.2017.
 */
public class AdminList implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user = (User) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");

        if (user != null && user.getIsAdmin()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(((HttpServletRequest) servletRequest).getContextPath() + "/");
        }
    }

    public void destroy() {

    }
}
