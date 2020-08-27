package com.kgc.travel.dao;

import com.kgc.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category findByCid(int cid);
}
