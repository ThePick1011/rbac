package com.westos.rbac.dao.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.westos.rbac.dao.RoleDao;
import com.westos.rbac.domain.Role;
import com.westos.rbac.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yihang
 */
public class RoleDaoImpl implements RoleDao {
    ModuleDaoImpl moduleDao = new ModuleDaoImpl();
   // RoleDao roleDao = new RoleDaoImpl();
    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        try(Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement("select * from rbac_role")){
                ResultSet set = statement.executeQuery();
                while (set.next()){
                    Role role = new Role();
                    role.setId(set.getInt("id"));
                    role.setName(set.getString("name"));
                    list.add(role);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Role> findByUserId(int userId) {
        List<Role> list = new ArrayList<>();
        try (Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement("SELECT a.ID, a.NAME FROM RBAC_ROLE a INNER JOIN RBAC_USER_ROLE b ON a.ID=b.ROLE_ID WHERE b.USER_ID=?")){
                statement.setInt(1, userId);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    Role role = new Role();
                    role.setId(set.getInt( "id"));
                    role.setName(set.getString("name"));
                    role.setModules(moduleDao.findByRoleId(role.getId()));
                    list.add(role);
                }
                return list;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteRoleModule(int roleId) {
        String sql = "DELETE FROM RBAC_ROLE_MODULE WHERE ROLE_ID=?";
        try(Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement(sql)){
                statement.setInt(1, roleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertRoleModule(int roleId, int moduleId) {
        String sql = "INSERT INTO RBAC_ROLE_MODULE (ROLE_ID, MODULE_ID) VALUES (?,?)";
        try(Connection con = JdbcUtil.getConnection()){
            try(PreparedStatement statement = con.prepareStatement(sql)){
                statement.setInt(1,roleId);
                statement.setInt(2,moduleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
