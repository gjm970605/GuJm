package com.kgc.travel.dao;

import com.kgc.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int routeCount(int cid,String rname);

    List<Route> findByPage(int cid, int startIndex, int pageSize, String rname);

    Route findByRid(int rid);

    List<Route> findInRid(List<Integer> ridList);

    void updateCount(int rid);

    int countGreaterZiro();

    List<Route> favoriteRankByPage(String rname, int min, int max, int startIndex, int pageSize);

    List<Route> orderByRdate(int startIndex, int pageSize);

    List<Route> findByCid(int cid, int startIndex, int pageSize);
}
