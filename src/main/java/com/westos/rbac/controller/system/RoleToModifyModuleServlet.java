package com.westos.rbac.controller.system;

import com.westos.rbac.dao.ModuleDao;
import com.westos.rbac.dao.impl.ModuleDaoImpl;
import com.westos.rbac.domain.Module;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yihang
 */
@WebServlet("/system/role/tomodifymodule")
public class RoleToModifyModuleServlet extends HttpServlet {
    ModuleDao moduleDao = new ModuleDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 补充跳至修改角色的模块表单的代码
        // 获取角色编号
        String roleId = req.getParameter("id");

        // 查询所有模块
        List<Module> modules = moduleDao.findAll();
        // 查询该角色所拥有的模块
        List<Module> roleModules = moduleDao.findByRoleId(Integer.parseInt(roleId));

        // 仅需要模块的编号集合
        List<Integer> ids = new ArrayList<>();
        for (Module roleModule : roleModules) {
            ids.add(roleModule.getId());
        }

        // 存入作用域
        req.setAttribute("roleModules", roleModules);
        req.setAttribute("modules", modules);
        req.setAttribute("ids", ids);

        // 转发
        req.getRequestDispatcher("/jsp/system/role/tomodifymodule.jsp").forward(req, resp);
    }
}
