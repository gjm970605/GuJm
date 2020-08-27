package com.kgc.travel.web.servlet;

import com.kgc.travel.domain.ResultInfo;
import com.kgc.travel.domain.User;
import com.kgc.travel.service.UserService;
import com.kgc.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取全部参数封装为user
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //使用service注册
        ResultInfo result= userService.register(user);

        //返回信息
        this.writeValue(result,request,response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //接收请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String auto_login = request.getParameter("auto_login");

        System.out.println("自动登录:"+auto_login);
        System.out.println(username+"=="+password);

        //使用service登录
        ResultInfo resultInfo = userService.login(username,password);

        //若登录成功按用户选择 设置自动登录
        if (resultInfo.isFlag()){

        }

        //将登录用户设置到session中
        request.getSession().setAttribute("curUser",resultInfo.getData());

        //返回result
        this.writeValue(resultInfo,request,response);
    }

    public void findCurUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得session中的用户
        User curUser = (User) request.getSession().getAttribute("curUser");

        //返回该对象session
        this.writeValue(curUser,request,response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("curUser");

        //跳转
        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取激活码
        String toActiveCode = request.getParameter("code");

        //使用服务激活此用户
        UserService service = new UserServiceImpl();
        boolean result = service.active(toActiveCode);

        if (result){
            //激活成功
            //提示激活成功信息后自动重定向到登录
            response.getWriter().write("<h2>您的账号已成功激活,即将跳转登录页面</h2>");
            response.setHeader("refresh","3;url=http://localhost/travel/login.html");
        }else {
            //激活失败
            response.getWriter().write("<h2>您的账号激活失败,请登陆官网联系客服咨询</h2><br>");
            response.getWriter().write("<h2>即将为您跳转课工场旅游网</h2>");
            response.setHeader("refresh","3;url=http://localhost/travel/index.html");
        }
    }
}
