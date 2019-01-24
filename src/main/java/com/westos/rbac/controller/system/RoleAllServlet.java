package com.westos.rbac.controller.system;

import com.westos.rbac.dao.RoleDao;
import com.westos.rbac.dao.impl.RoleDaoImpl;
import com.westos.rbac.domain.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yihang
 */
@WebServlet("/system/role/all")
public class RoleAllServlet extends HttpServlet {
    RoleDao roleDao = new RoleDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 补充跳至查询所有角色的代码
        List<Role> list = roleDao.findAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/jsp/system/role/all.jsp").forward(req,resp);
    }
}
