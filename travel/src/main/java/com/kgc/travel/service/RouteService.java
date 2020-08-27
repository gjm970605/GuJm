package com.kgc.travel.service;

import com.kgc.travel.domain.PageBean;
import com.kgc.travel.domain.Route;

import java.util.List;

public interface RouteService {
    PageBean<Route> findPage(int cid, int currentPage, int pageSize,String rname);

    Route findRoute(int rid);

    List<Route> popularRoute();

    List<Route> newestRoute();

    List<Route> internalRoute();

    List<Route> abroadRoute();

    List<Route> recommendRoute();
}
