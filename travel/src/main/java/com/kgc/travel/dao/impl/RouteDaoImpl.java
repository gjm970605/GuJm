package com.kgc.travel.dao.impl;

import com.kgc.travel.dao.RouteDao;
import com.kgc.travel.domain.Route;
import com.kgc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int routeCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where cid like ? and rname like ?  group by cid";
        int result = 0;
        if (!"%".equals(rname)){
            rname = "%"+rname+"%";
        }
        try {
            if (cid == -1){
                result = template.queryForObject(sql,Integer.class,"%",rname);
            }else result = template.queryForObject(sql,Integer.class,cid,rname);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public List<Route> findByPage(int cid, int startIndex, int pageSize, String rname) {
        String sql = "select * from tab_route where cid like ? and rname like ? limit ?,?";
        if (!"%".equals(rname)){
            rname = "%"+rname+"%";
        }
        List<Route> query;
        if (cid == -1){
            query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), "%", rname, startIndex, pageSize);
        }else{
            query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, rname, startIndex, pageSize);
        }
        return query;
    }

    @Override
    public Route findByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = null;
        try {
            route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        } catch (DataAccessException e) {

        }
        return route;
    }

    @Override
    public List<Route> findInRid(List<Integer> ridList) {
        String sql = "select * from tab_route where rid in (-1";
        StringBuilder temp = new StringBuilder(sql);
        for (Integer integer : ridList) {
            temp.append(","+integer);
        }
        temp.append(")");
        sql = temp.toString();

        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        return query;
    }

    @Override
    public void updateCount(int rid) {
        String sql = "update tab_route set count = ? where rid =?";
        int count = findCount(rid) + 1;
        template.update(sql,count,rid);
    }

    @Override
    public int countGreaterZiro() {
        String sql = "select count(*) from tab_route where count > 0";
        int result = 0;
        try {
            result = template.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {

        }
        return result;

    }

    @Override
    public List<Route> favoriteRankByPage(String rname, int min, int max, int startIndex, int pageSize) {
        String sql = "select * from tab_route where count > 0 and rname like ? and price between ? and ? order by count desc limit ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), rname, min, max, startIndex, pageSize);
        return query;
    }

    @Override
    public List<Route> orderByRdate(int startIndex, int pageSize) {
        String sql = "select * from tab_route order by rdate desc limit ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), startIndex, pageSize);
        return query;
    }

    @Override
    public List<Route> findByCid(int cid, int startIndex, int pageSize) {
        String sql = "select * from tab_route where cid = ? limit ?,?";
        List<Route> query = template.query(sql,new BeanPropertyRowMapper<>(Route.class),cid,startIndex,pageSize);
        return query;
    }

    public Integer findCount(int rid){
        String sql = "select count from tab_route where rid = ?";
        int result = 0;
        try {
            result = template.queryForObject(sql,Integer.class,rid);
        } catch (DataAccessException e) {

        }
        return result;
    }
}
