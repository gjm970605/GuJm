package com.kgc.travel.web.servlet;

import com.kgc.travel.domain.PageBean;
import com.kgc.travel.domain.ResultInfo;
import com.kgc.travel.domain.Route;
import com.kgc.travel.domain.User;
import com.kgc.travel.service.FavoriteService;
import com.kgc.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    //展示收藏按钮
    public void favoriteButton(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String str_rid = request.getParameter("rid");
        if (str_rid == null || str_rid.length() == 0 || "null".equals(str_rid)){
            str_rid = "0";
        }
        int rid = Integer.parseInt(str_rid);
        User curUser = (User) request.getSession().getAttribute("curUser");

        //结果对象
        ResultInfo result = new ResultInfo();

        if (curUser != null){
            //登陆状态，判断是否已收藏
            boolean isFavorite = favoriteService.isFavorite(rid,curUser.getUid());
            result.setFlag(!isFavorite);//已收藏则不展示 没收藏则展示
        }else result.setFlag(true);//未登录展示按钮

        //返回结果
        this.writeValue(result,request,response);

    }

    //添加收藏
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String str_rid = request.getParameter("rid");
        if (str_rid == null || str_rid.length() == 0 || "null".equals(str_rid)){
            str_rid = "0";
            return;
        }
        int rid = Integer.parseInt(str_rid);
        User curUser = (User) request.getSession().getAttribute("curUser");

        ResultInfo result = new ResultInfo();

        if (curUser == null){
            result.setFlag(false);
        }else {
            favoriteService.addFavorite(rid,curUser.getUid());
            result.setFlag(true);
        }

        //返回结果
        this.writeValue(result,request,response);
    }

    //查询当前用户的收藏信息
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String str_uid = request.getParameter("uid");
        if (str_uid == null || str_uid.length() == 0 || "null".equals(str_uid)){
            str_uid = "-1";
        }
        String str_currentPage = request.getParameter("currentPage");
        if (str_currentPage == null || str_currentPage.length() == 0||"null".equals(str_currentPage)){
            str_currentPage = "1";
        }
        String str_pageSize = request.getParameter("pageSize");
        if (str_pageSize == null || str_pageSize.length() == 0 || "null".equals(str_pageSize)){
            str_pageSize = "12";
        }

        int uid = Integer.parseInt(str_uid);
        int currentPage = Integer.parseInt(str_currentPage);
        int pageSize = Integer.parseInt(str_pageSize);

        //使用serivice得到page
        PageBean<Route> page = favoriteService.favoritePage(uid,currentPage,pageSize);

        //json序列化返回
        this.writeValue(page,request,response);
    }

    public void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取各项参数
        String str_currentPage = request.getParameter("currentPage");
        String str_pageSize = request.getParameter("pageSize");
        String rname = request.getParameter("search_rname");
        String search_min = request.getParameter("search_min");
        String search_max = request.getParameter("search_max");

        //处理数据
        if (str_currentPage == null || str_currentPage.length() == 0 || "null".equals(str_currentPage)){
            str_currentPage = "1";
        }
        int currentPage = Integer.parseInt(str_currentPage);

        if (str_pageSize == null || str_pageSize.length() == 0 || "null".equals(str_pageSize)){
            str_pageSize = "8";
        }
        int pageSize = Integer.parseInt(str_pageSize);

        int min;
        if (search_min == null || search_min.length() == 0 || "null".equals(search_min)){
            min = Integer.MIN_VALUE;
        }else min = Integer.parseInt(search_min);

        int max;
        if (search_max == null || search_max.length() == 0 || "null".equals(search_max)){
            max = Integer.MAX_VALUE;
        }else max = Integer.parseInt(search_max);

        if (rname == null || rname.length() == 0 || "null".equals(rname)){
            rname = "%";
        }else rname = "%" + rname + "%";

        //使用service获得page
        PageBean<Route> page = favoriteService.rankPage(currentPage,pageSize,rname,min,max);

        //返回数据
        this.writeValue(page,request,response);

    }
}
