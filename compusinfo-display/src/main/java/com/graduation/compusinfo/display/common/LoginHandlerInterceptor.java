package com.graduation.compusinfo.display.common;

import com.graduation.compusinfo.display.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zzhengkai
 * @date 2019/12/4 19:02
 */
@Component
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String struuid = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("USER_TOKEN")) {
                struuid = cookie.getValue();
            }
        }
        if(struuid == null){
            //未登陆，返回登陆页面
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                //如果是ajax请求响应头会有x-requested-with,自定义707状态码为ajax登录过期
                log.info("ajax被拦截");
                response.setHeader("SESSIONSTATUS", "TIMEOUT");
                response.setHeader("CONTEXTPATH", request.getContextPath() +"/admin/login");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403 禁止
                response.setStatus(707);
                PrintWriter out = response.getWriter();
                out.print(request.getContextPath() + "/admin/login");
                out.flush();
            }else{
                request.setAttribute("msg","没有权限请先登陆");
                response.sendRedirect("/admin/login");
//                request.getRequestDispatcher(request.getContextPath()+"/admin/login").forward(request,response);
            }
            return false;
        }else{
            //判断该cookie是否正确，或者是否登录超时
            String userJoin = redisService.getUserFromRedis(struuid);
            if(userJoin==null || "".equals(userJoin) ){
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                    //如果是ajax请求响应头会有x-requested-with,自定义707状态码为ajax登录过期
                    log.info("ajax被拦截");
                    response.setHeader("SESSIONSTATUS", "TIMEOUT");
                    response.setHeader("CONTEXTPATH", request.getContextPath() +"/admin/login");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403 禁止
                    response.setStatus(707);
                    PrintWriter out = response.getWriter();
                    out.print(request.getContextPath() + "/admin/login");
                    out.flush();
                }else{
                    request.setAttribute("msg","没有权限请先登陆");
                    request.getRequestDispatcher(request.getContextPath()+"/admin/login").forward(request,response);
                }
                return false;
            }
            return true;
        }

//        Object user = request.getSession().getAttribute("user-token");
//        if(user == null){
//            //未登陆，返回登陆页面
//            request.setAttribute("msg","没有权限请先登陆");
//            request.getRequestDispatcher(request.getContextPath()+"/campus/login").forward(request,response);
//            return false;
//        }else{
//            //已登陆，放行请求
//            //token格式：campus/username/passwork;
//            return true;
//        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
