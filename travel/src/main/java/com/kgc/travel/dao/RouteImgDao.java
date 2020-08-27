package com.kgc.travel.dao;

import com.kgc.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
