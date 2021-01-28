package com.YoRHa.crm.settings.web.controller;

import com.YoRHa.crm.exception.LoginException;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.settings.service.UserService;
import com.YoRHa.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 15:15
 * Versions:1.0.0
 * Description:
 */

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/page/loginPage.do")
    public String userLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/settings/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userLogin(User user, HttpServletRequest request) throws LoginException {
        Map<String, Object> map = new HashMap<>();

        String ip = request.getRemoteAddr();
        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
        user = userService.userLogin(user, ip);

        request.getSession().setAttribute("user", user);
        map.put("result", true);

        return map;
    }

    @RequestMapping(value = "/page/workbenchPage.do")
    public String gotoWorkspacePage(){
        return "workbench/index";
    }
}
