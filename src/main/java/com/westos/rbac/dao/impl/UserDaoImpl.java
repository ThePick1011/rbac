package com.westos.rbac.dao.impl;

import com.mysql.fabric.FabricStateResponse;
import com.westos.rbac.dao.UserDao;
import com.westos.rbac.domain.User;
import com.westos.rbac.util.JdbcUtil;
import com.westos.rbac.util.StringUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yihang
 */
public class UserDaoImpl implements UserDao {
    RoleDaoImpl roleDao = new RoleDaoImpl();
    @Override
    public User findByUsername(String username) {
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("SELECT a.ID,a.USERNAME,a.PASSWORD,a.ORG_ID,a.ORG_IDS FROM rbac_user a WHERE a.USERNAME=?")) {
                statement.setString(1, username);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    User user = new User();
                    user.setId(set.getInt("ID"));
                    user.setUsername(set.getString("USERNAME"));
                    user.setPassword(set.getString("PASSWORD"));
                    user.setOrgId(set.getInt("ORG_ID"));
                    String str = set.getString("ORG_IDS");
                    Integer[] orgIds = StringUtil.split(str);
                    user.setOrgIds(orgIds);

                    user.setRoles(roleDao.findByUserId(user.getId()));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(int userId) {
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("SELECT a.ID,a.USERNAME,a.PASSWORD,a.ORG_ID,a.ORG_IDS FROM rbac_user a WHERE a.ID=?")) {
                statement.setInt(1, userId);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    User user = new User();
                    user.setId(set.getInt("ID"));
                    user.setUsername(set.getString("USERNAME"));
                    user.setPassword(set.getString("PASSWORD"));
                    user.setOrgId(set.getInt("ORG_ID"));
                    String str = set.getString("ORG_IDS");
                    Integer[] orgIds = StringUtil.split(str);
                    user.setOrgIds(orgIds);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("SELECT * FROM rbac_user")) {
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    User user = new User();
                    user.setId(set.getInt("ID"));
                    user.setUsername(set.getString("USERNAME"));
                    user.setPassword(set.getString("PASSWORD"));
                    user.setOrgId(set.getInt("ORG_ID"));
                    String str = set.getString("ORG_IDS");
                    Integer[] orgIds = StringUtil.split(str);
                    user.setOrgIds(orgIds);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<User> findByPage(int page, int rows) {
        List<User> users = new ArrayList<>();
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("SELECT * FROM rbac_user limit ?,?")) {
                statement.setInt(1, (page - 1) * rows);
                statement.setInt(2, rows);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    User user = new User();
                    user.setId(set.getInt("ID"));
                    user.setUsername(set.getString("USERNAME"));
                    user.setPassword(set.getString("PASSWORD"));
                    user.setOrgId(set.getInt("ORG_ID"));
                    String str = set.getString("ORG_IDS");
                    Integer[] orgIds = StringUtil.split(str);
                    user.setOrgIds(orgIds);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public int findCount() {
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("SELECT count(*) FROM rbac_user")) {
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    return set.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void insert(User user) {
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("insert into rbac_user(username,password,org_id,org_ids)values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setInt(3, user.getOrgId());
                Integer[] orgIds = user.getOrgIds();
                String s = StringUtil.join(orgIds);
                statement.setString(4,s);
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                user.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("update rbac_user set username=?,password=? where id=?; ")) {
                statement.setString(1,user.getUsername());
                statement.setString(2,user.getPassword());
                statement.setInt(3,user.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int userId) {
        try (Connection con = JdbcUtil.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("delete from rbac_user where id=? ")) {
                statement.setInt(1,userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserRole(int userId) {
        try(Connection conn = JdbcUtil.getConnection()) {
            try(PreparedStatement psmt = conn.prepareStatement("delete from rbac_user_role where user_id = ?")) {
                psmt.setInt(1, userId);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean exists(String username){
        try(Connection con = JdbcUtil.getConnection()) {
            try(PreparedStatement statement  = con.prepareStatement("select  count(*) from rbac_user where username =?")){
                statement.setString(1,username);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getInt(1) >= 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public void insertUserRole(int userId, int roleId) {
        try(Connection conn = JdbcUtil.getConnection()) {
            try(PreparedStatement psmt = conn.prepareStatement("insert into rbac_user_role(user_id,role_id) values (?,?)")) {
                psmt.setInt(1, userId);
                psmt.setInt(2, roleId);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
