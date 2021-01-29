package com.YoRHa.crm.workbench.web.handler.exception;

import com.YoRHa.crm.exception.SqlDataDeleteException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-29 13:37
 * Versions:1.0.0
 * Description:
 */

@ControllerAdvice
public class SqlDataExceptionHandler {

    @ExceptionHandler(SqlDataDeleteException.class)
    @ResponseBody
    public Map<String, Object> sqlDataDeleteException(Exception e){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", e.getMessage());
        map.put("result", false);
        return map;
    }
}
