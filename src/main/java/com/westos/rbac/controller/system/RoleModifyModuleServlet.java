package com.westos.rbac.controller.system;

import com.westos.rbac.dao.RoleDao;
import com.westos.rbac.dao.impl.RoleDaoImpl;
import com.westos.rbac.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yihang
 */
@WebServlet("/system/role/modifymodule")
public class RoleModifyModuleServlet extends HttpServlet {
    RoleDao roleDao = new RoleDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO 补充修改角色的模块的代码
        String roleIdStr = req.getParameter("roleId");
        String[] modudleIdStr = req.getParameterValues("moduleId");

        // 进行修改
        roleDao.deleteRoleModule(Integer.parseInt(roleIdStr));
        int[] ints = StringUtil.strToInt(modudleIdStr);
        for (int i : ints) {
            roleDao.insertRoleModule(Integer.parseInt(roleIdStr),i);
        }
        // 修改成功，回到分页页面
        resp.sendRedirect("all");
    }
}
