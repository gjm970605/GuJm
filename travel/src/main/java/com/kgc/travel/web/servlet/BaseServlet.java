package com.kgc.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgc.travel.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        //获取调用方法
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);

        System.out.println("请求的方法名称"+methodName);

        //执行方法
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //响应写回jason
    public void writeValue(Object obj,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        JsonUtil.getMapper().writeValue(resp.getOutputStream(),obj);
    }

    //转换json
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        return JsonUtil.getMapper().writeValueAsString(obj);
    }
}
