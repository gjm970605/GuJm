package com.kgc.travel.dao.impl;

import com.kgc.travel.dao.CategoryDao;
import com.kgc.travel.domain.Category;
import com.kgc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return template.query(sql,new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Category findByCid(int cid) {
        String sql = "select * from tab_category where cid = ?";
        Category query = null;
        try {
            query = template.queryForObject(sql,new BeanPropertyRowMapper<>(Category.class),cid);
        } catch (DataAccessException e) {

        }
        return query;
    }
}
