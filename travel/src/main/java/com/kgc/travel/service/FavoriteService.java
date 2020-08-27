package com.kgc.travel.service;

import com.kgc.travel.domain.PageBean;
import com.kgc.travel.domain.Route;

public interface FavoriteService {
    boolean isFavorite(int rid, int uid);

    void addFavorite(int rid, int uid);

    PageBean<Route> favoritePage(int uid, int currentPage, int pageSize);

    PageBean<Route> rankPage(int currentPage, int pageSize, String rname, int min, int max);
}
