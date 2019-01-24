package com.westos.rbac.dao.impl;

import com.westos.rbac.dao.ModuleDao;
import com.westos.rbac.domain.Module;
import com.westos.rbac.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author yihang
 */
public class ModuleDaoImpl implements ModuleDao {
    @Override
    public List<Module> findAll() {

        try(Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement("select * from rbac_module")){
                List<Module> all = new ArrayList<>();
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    Module module = new Module();
                    module.setPid(resultSet.getInt("pid"));
                    module.setName(resultSet.getString("name"));
                    module.setCode(resultSet.getString("code"));
                    module.setId(resultSet.getInt("id"));
                    all.add(module);
                }
                List<Module> list1 = new ArrayList<>();
                Map<Integer,Module> moduleMap = new HashMap<>();//key is module pid value is module
                for (Module module : all) {
                    if (module.getPid() == 0) {
                        list1.add(module);
                    }
                    moduleMap.put(module.getId(),module);
                }
                for (Module module : all) {
                    int pid = module.getPid();
                    Module parents = moduleMap.get(pid);
                    if(parents!=null){
                        parents.getChildren().add(module);
                    }
                }
                return list1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Module> findByRoleId(int roleId) {
        List<Module> list = new ArrayList<>();

        try(Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement("SELECT a.ID, a.NAME,a.CODE,a.PID FROM RBAC_MODULE a INNER JOIN RBAC_ROLE_MODULE b on a.ID=b.MODULE_ID WHERE b.ROLE_ID=?")){
                statement.setInt(1,roleId);
                ResultSet set = statement.executeQuery();
                while (set.next()){
                    Module module = new Module();
                    module.setId(set.getInt("id"));
                    module.setName(set.getString("name"));
                    module.setCode(set.getString("code"));
                    module.setPid(set.getInt("pid"));
                    list.add(module);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Module findModele(int moduleId) {
        List<Module> list = new ArrayList<>();

        try(Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement("select * from rbac_module where id=?")){
                statement.setInt(1,moduleId);
                ResultSet set = statement.executeQuery();
                Module module = new Module();
                if(set.next()){
                   module.setName(set.getString("name"));
                   module.setId(set.getInt("id"));
                }
                return module;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
