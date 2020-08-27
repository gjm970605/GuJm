package com.kgc.travel.service.impl;

import com.kgc.travel.dao.*;
import com.kgc.travel.dao.impl.*;
import com.kgc.travel.domain.*;
import com.kgc.travel.service.RouteService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> findPage(int cid, int currentPage, int pageSize, String rname) {
        int totalCount = routeDao.routeCount(cid,rname);
        //计算总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;

        //获取当前页数据
        int startIndex = (currentPage-1) * pageSize;
        List<Route> data = routeDao.findByPage(cid,startIndex,pageSize,rname);
        return new PageBean<Route>(totalCount,totalPage,currentPage,pageSize,data);
    }

    @Override
    public Route findRoute(int rid) {
        //查询详情
        Route detail = routeDao.findByRid(rid);

        //取图片
        List<RouteImg> imgList = routeImgDao.findByRid(rid);
        detail.setRouteImgList(imgList);

        //取目录对象
        Category category = categoryDao.findByCid(detail.getCid());
        detail.setCategory(category);

        //根据sid取卖家信息
        Seller seller = sellerDao.findBySid(detail.getSid());
        detail.setSeller(seller);

        //查询收藏次数
        int count = favoriteDao.countByRid(rid);
        detail.setCount(count);

        return detail;
    }

    @Override
    public List<Route> popularRoute() {
        List<Route> routeList = routeDao.favoriteRankByPage("%", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 4);
        return routeList;
    }

    @Override
    public List<Route> newestRoute() {
        List<Route> routeList = routeDao.orderByRdate(0,4);
        return routeList;
    }

    @Override
    public List<Route> internalRoute() {
        List<Route> routeList = routeDao.findByCid(5,0,6);
        return routeList;
    }

    @Override
    public List<Route> abroadRoute() {
        List<Route> routeList = routeDao.findByCid(4,0,6);
        return routeList;
    }

    @Override
    public List<Route> recommendRoute() {
        List<Route> newestList = routeDao.orderByRdate(0, 3);
        List<Route> routeList = routeDao.favoriteRankByPage("%", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 3);

        routeList.addAll(newestList);
        return routeList;
    }
}
