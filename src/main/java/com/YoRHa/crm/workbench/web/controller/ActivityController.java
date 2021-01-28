package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @RequestMapping(value = "/listUser.do")
    @ResponseBody
    public List<User> listUser(){

        return activityService.listUser();
    }

    @RequestMapping(value = "/add.do")
    @ResponseBody
    public Boolean activityAdd(Activity activity){

        return activityService.activityAdd(activity);
    }

    @RequestMapping(value = "/list.do")
    @ResponseBody
    public Map<String, Object> activityList(Activity activity, Integer pageNo, Integer pageSize){

        return activityService.searchActivityList(pageNo, pageSize, activity);
    }
}
