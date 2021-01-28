package com.YoRHa.crm.settings.web.handler.exception;

import com.YoRHa.crm.exception.LoginException;
import com.YoRHa.crm.settings.dao.UserDao;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 15:07
 * Versions:1.0.0
 * Description:
 */

@ControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map<String, Object> loginException(Exception e){

        Map<String, Object> map = new HashMap<>();
        map.put("result", false);
        map.put("msg", e.getMessage());
        return map;
    }
}
