package com.kgc.travel.dao.impl;

import com.kgc.travel.dao.UserDao;
import com.kgc.travel.domain.User;
import com.kgc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
        }
        return user;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        int update = template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
        return update > 0;
    }

    @Override
    public User findByCode(String toActiveCode) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),toActiveCode);
        } catch (Exception e) {
        }
        return user;
    }

    @Override
    public boolean active(User findUser) {
        String sql = "update tab_user set status = 'Y' where uid = ? ";
        int update = template.update(sql, findUser.getUid());
        return update > 0;
    }
}
