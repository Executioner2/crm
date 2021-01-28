package com.YoRHa.crm.settings.web.handler.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 16:52
 * Versions:1.0.0
 * Description:
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();
        if(session == null || session.getAttribute("user") == null){
            if("/page/loginPage".equals(path)){
                return true;
            }else if("/settings/login".equals(path)){
                return true;
            }else if("/crm".equals(path)){
                return true;
            }else{
                request.getRequestDispatcher("/page/loginPage").forward(request, response);
                return false;
            }
        }
        return true;
    }
}
