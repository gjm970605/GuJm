package com.kgc.travel.web.servlet;

import com.kgc.travel.domain.PageBean;
import com.kgc.travel.domain.Route;
import com.kgc.travel.service.RouteService;
import com.kgc.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();

    public void page(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String str_cid = request.getParameter("cid");
        String str_currentPage = request.getParameter("currentPage");
        String str_pageSize = request.getParameter("pageSize");
        String rname = request.getParameter("rname");

        //处理参数 默认值
        if (str_cid == null || str_cid.length() == 0 || "null".equals(str_cid)){
            str_cid = "-1";
        }
        int cid = Integer.parseInt(str_cid);

        if (str_currentPage == null || str_currentPage.length() == 0 || "null".equals(str_currentPage)){
            str_currentPage = "1";
        }
        int currentPage = Integer.parseInt(str_currentPage);

        if (str_pageSize == null || str_pageSize.length() == 0 || "null".equals(str_pageSize)){
            str_pageSize = "10";
        }
        int pageSize = Integer.parseInt(str_pageSize);

        if (rname == null || rname.length() == 0 || "null".equals(rname)){
            rname = "%";
        }

        //使用service获得page
        PageBean<Route> page =service.findPage(cid,currentPage,pageSize,rname);

        //json序列化返回
        this.writeValue(page,request,response);
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //接收前端传入rid
        String str_rid = request.getParameter("rid");
        //处理数据
        if (str_rid == null || str_rid.length() == 0 || "null".equals(str_rid)){
            str_rid = "1";
        }
        int rid = Integer.parseInt(str_rid);

        //调用service得到route对象
        Route detail = service.findRoute(rid);

        //返回结果
        this.writeValue(detail,request,response);
    }

    //人气旅游请求
    public void popularity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用服务获得收藏最多的4个线路
        List<Route> list =  service.popularRoute();

        //返回信息
        this.writeValue(list,request,response);
    }

    //最新旅游请求
    public void newest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用服务获得最新添加的四个线路
        List<Route> list =  service.newestRoute();

        //返回信息
        this.writeValue(list,request,response);
    }

    //首页国内请求
    public void internal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用服务获得国内6个线路
        List<Route> list =  service.internalRoute();

        //返回信息
        this.writeValue(list,request,response);
    }

    //首页境外游
    public void abroad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用服务获得境外6个线路
        List<Route> list =  service.abroadRoute();

        //返回信息
        this.writeValue(list,request,response);
    }

    public void recommend(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用服务获取收藏最多的三个 和最新的三个路线
        List<Route> list =  service.recommendRoute();

        //返回信息
        this.writeValue(list,request,response);
    }
}
