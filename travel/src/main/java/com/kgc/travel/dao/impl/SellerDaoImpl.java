package com.kgc.travel.dao.impl;

import com.kgc.travel.dao.SellerDao;
import com.kgc.travel.domain.Seller;
import com.kgc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller query = null;
        try {
            query = template.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),sid);
        } catch (DataAccessException e) {

        }
        return query;
    }
}
