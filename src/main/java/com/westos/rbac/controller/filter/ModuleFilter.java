package com.westos.rbac.controller.filter;

import com.westos.rbac.domain.Module;
import com.westos.rbac.domain.Role;
import com.westos.rbac.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter({"/system/*", "/order/*", "/product/*"})
public class ModuleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //权限拦截
        Object user = req.getSession().getAttribute("principal");
        if (user != null) {
            List<Role> roles = ((User) user).getRoles();
            List<Module> moduleList = new ArrayList<>();
            List<String> moduleCode = new ArrayList<>();
            for (Role role : roles) {
                List<Module> modules = role.getModules();
                moduleList.addAll(modules);
            }
            for (Module module: moduleList) {
                String code = module.getCode();
                if(code.equals("/system/role/all")){
                    code = "/system/role";
                }else if(code.equals("/system/user/page")){
                    code = "/system/user/";
                }
                moduleCode.add(code);
            }
            for (String code : moduleCode) {

                if (req.getRequestURI().startsWith(code)) {
                    filterChain.doFilter(req, servletResponse);
                    return;
                }
            }
            resp.sendError(403);
        }
    }

    @Override
    public void destroy() {

    }
}
