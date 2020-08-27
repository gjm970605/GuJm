package com.kgc.travel.service.impl;

import com.kgc.travel.dao.FavoriteDao;
import com.kgc.travel.dao.RouteDao;
import com.kgc.travel.dao.impl.FavoriteDaoImpl;
import com.kgc.travel.dao.impl.RouteDaoImpl;
import com.kgc.travel.domain.Favorite;
import com.kgc.travel.domain.PageBean;
import com.kgc.travel.domain.Route;
import com.kgc.travel.service.FavoriteService;

import java.util.Date;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite query = favoriteDao.findByRidAndUid(rid , uid);

        return query != null;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        Date date = new Date();
        favoriteDao.addFavorite(rid,date,uid);
        routeDao.updateCount(rid);
    }

    @Override
    public PageBean<Route> favoritePage(int uid, int currentPage, int pageSize) {
        PageBean<Route> page = new PageBean<>();
        int totalCount = favoriteDao.countByUid(uid);
        int totalPage = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;

        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);

        int startIndex = (currentPage - 1) * pageSize;
        List<Integer> ridList = favoriteDao.pageByUid(uid, startIndex, pageSize);

        List<Route> routeList = routeDao.findInRid(ridList);

        page.setPageData(routeList);

        return page;
    }

    @Override
    public PageBean<Route> rankPage(int currentPage, int pageSize, String rname, int min, int max) {
        PageBean<Route> page = new PageBean<>();
        int totalCount = routeDao.countGreaterZiro();
        int totalPage = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;

        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);

        int startIndex = (currentPage - 1) * pageSize;

        List<Route> routeList = routeDao.favoriteRankByPage(rname,min,max,startIndex,pageSize);

        page.setPageData(routeList);

        return page;
    }
}
