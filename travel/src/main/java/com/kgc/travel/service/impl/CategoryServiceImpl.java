package com.kgc.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kgc.travel.dao.CategoryDao;
import com.kgc.travel.dao.impl.CategoryDaoImpl;
import com.kgc.travel.domain.Category;
import com.kgc.travel.service.CategoryService;
import com.kgc.travel.util.JedisUtil;
import com.kgc.travel.util.JsonUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();

    @Override
    public List<Category> findAll() throws IOException {
        Set<Tuple> set = jedis.zrangeWithScores("category", 0, -1);
        if (set == null || set.size() == 0){
            //没有缓存，从dao层获取后存入缓存
            List<Category> all = dao.findAll();
            for (Category category : all) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
            System.out.println("从数据库获取");
            return all;
        }else {
            //获取到了缓存
            List<Category> result = new ArrayList<>();
            for (Tuple tuple : set) {
                result.add(new Category((int) tuple.getScore(),tuple.getElement()));
            }
            System.out.println("从缓存获取");
            System.out.println(result);
            return result;
        }
    }
}
