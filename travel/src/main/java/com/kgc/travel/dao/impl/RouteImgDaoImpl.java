package com.kgc.travel.dao.impl;

import com.kgc.travel.dao.RouteImgDao;
import com.kgc.travel.domain.RouteImg;
import com.kgc.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        List<RouteImg> query = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
            return query;
    }
}
