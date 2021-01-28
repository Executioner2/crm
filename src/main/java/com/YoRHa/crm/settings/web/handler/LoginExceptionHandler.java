package com.YoRHa.crm.settings.web.handler;

import com.YoRHa.crm.exception.LoginException;
import com.YoRHa.crm.settings.dao.UserDao;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String loginException(Exception e){
        return e.getMessage();
    }
}
