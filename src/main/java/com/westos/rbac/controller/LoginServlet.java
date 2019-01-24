package com.westos.rbac.controller;

import com.westos.rbac.dao.ModuleDao;
import com.westos.rbac.dao.RoleDao;
import com.westos.rbac.dao.UserDao;
import com.westos.rbac.dao.impl.ModuleDaoImpl;
import com.westos.rbac.dao.impl.RoleDaoImpl;
import com.westos.rbac.dao.impl.UserDaoImpl;
import com.westos.rbac.domain.Module;
import com.westos.rbac.domain.Role;
import com.westos.rbac.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    UserDao userDao = new UserDaoImpl();
    ModuleDao moduleDao = new ModuleDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.equals("")) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error");
            return;
        }
        if (password == null || password.equals("")) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error");
            return;
        }
        User user = userDao.findByUsername(username);
        if (user == null) {
            resp.sendRedirect(req.getContextPath()+"/login.jsp?erro");
            return;
        }
        if(!user.getPassword().equals(password)){
            resp.sendRedirect(req.getContextPath()+"/login.jsp?erro");
            return;
        }
        req.getSession().setAttribute("principal",user);

        List<Role> roles = user.getRoles();
        List<Module> lists = new ArrayList<>();
        for (Role role : roles) {
            List<Module> modules = role.getModules();
            lists.addAll(modules);
        }

        req.getSession().setAttribute("moduleList",lists);
        HashSet<Module> pModuleSet = new HashSet<>();
        for (Module list : lists) {
            Module modele = moduleDao.findModele(list.getPid());
            pModuleSet.add(modele);
        }

        req.getSession().setAttribute("pModuleSet",pModuleSet);
        resp.sendRedirect(req.getContextPath()+"/index.jsp");
    }
}
