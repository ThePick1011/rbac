package com.westos.rbac.controller.system;

import com.westos.rbac.dao.UserDao;
import com.westos.rbac.dao.impl.UserDaoImpl;
import com.westos.rbac.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Year;
import java.util.List;

/**
 * @author yihang
 */
@WebServlet("/system/user/page")
public class UserPageServlet extends HttpServlet {
    UserDao userDao = new UserDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int rows = 3;
        String page1 = req.getParameter("page");
        if (page1 != null) {
            page = new Integer(page1);
        }
        List<User> byPage = userDao.findByPage(page, rows);
        req.setAttribute("list", byPage);//本页内容
        req.setAttribute("page",page);//页号
        int count = (userDao.findCount() - 1)/rows + 1;

        req.setAttribute("total",count);//总页数
        req.getRequestDispatcher("/jsp/system/user/page.jsp").forward(req, resp);
    }
}
