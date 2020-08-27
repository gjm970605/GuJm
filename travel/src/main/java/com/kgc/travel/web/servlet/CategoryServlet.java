package com.kgc.travel.web.servlet;

import com.kgc.travel.domain.Category;
import com.kgc.travel.domain.ResultInfo;
import com.kgc.travel.service.CategoryService;
import com.kgc.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    public void findAllCate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取全部category
        List<Category> list = service.findAll();

        //返回目录
        this.writeValue(list,request,response);
    }
}
