package com.kgc.travel.dao.impl;

import com.kgc.travel.dao.FavoriteDao;
import com.kgc.travel.domain.Favorite;
import com.kgc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int countByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ? group by rid";
        int count = 0;
        try {
            count = template.queryForObject(sql,Integer.class,rid);
        } catch (DataAccessException e) {

        }
        return count;
    }

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ? ";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    @Override
    public void addFavorite(int rid, Date date, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,date,uid);
    }

    @Override
    public int countByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ? group by uid";
        int count = 0;
        try {
            count = template.queryForObject(sql,Integer.class,uid);
        } catch (DataAccessException e) {

        }
        return count;
    }

    @Override
    public List<Integer> pageByUid(int uid, int startIndex, int pageSize) {
        String sql = "select rid from tab_favorite where uid = ? limit ?,?";

        List<Favorite> query = template.query(sql, new BeanPropertyRowMapper<>(Favorite.class), uid, startIndex, pageSize);
        List<Integer> result = new ArrayList<>();
        for (Favorite favorite : query) {
            result.add(favorite.getRid());
        }
        return result;
    }
}
