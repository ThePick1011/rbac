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

/**
 * @author yihang
 */
@WebServlet("/system/user/update")
public class UserUpdateServlet extends HttpServlet {
    UserDao userDao = new UserDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 补充修改用户的代码
        req.setCharacterEncoding("utf-8");
        String idStr = req.getParameter("id");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String orgIdStr = req.getParameter("orgId");
        User user = new User();
        user.setId(Integer.parseInt(idStr));
        user.setUsername(username);
        user.setPassword(password);
        user.setOrgId(Integer.parseInt(orgIdStr));
        userDao.update(user);
        resp.sendRedirect("page");
    }
}
