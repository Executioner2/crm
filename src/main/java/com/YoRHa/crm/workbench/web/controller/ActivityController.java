package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.exception.SqlDataDeleteException;
import com.YoRHa.crm.settings.dao.UserDao;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.settings.service.UserService;
import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 18:22
 * Versions:1.0.0
 * Description:
 */

@Controller
@RequestMapping(value = "/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/listUser.do")
    @ResponseBody
    public List<User> listUser(){

        return userService.listUserName();
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean activityAdd(Activity activity){

        return activityService.activityAdd(activity);
    }

    @RequestMapping(value = "/list.do")
    @ResponseBody
    public Map<String, Object> activityList(Activity activity, Integer pageNo, Integer pageSize){

        return activityService.searchActivityList(pageNo, pageSize, activity);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> activityDelete(String[] id) throws SqlDataDeleteException {
        Map<String, Object> map = new HashMap<>();

        Boolean result = activityService.activityDeleteById(id);
        map.put("result", result);

        return map;
    }

    @RequestMapping(value = "/query.do")
    @ResponseBody
    public Map<String, Object> activityQuery(Activity activity){

        return activityService.queryActivityById(activity);
    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateActivity(Activity activity, HttpServletRequest request){
        String userName = ((User)request.getSession(false).getAttribute("user")).getName();
        activity.setEditBy(userName);
        activity.setEditTime(DateTimeUtil.getSysDate());

        return activityService.updateActivity(activity);
    }
}
