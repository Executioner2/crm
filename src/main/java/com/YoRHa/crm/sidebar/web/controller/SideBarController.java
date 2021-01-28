package com.YoRHa.crm.sidebar.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 16:31
 * Versions:1.0.0
 * Description:
 */

@Controller
@RequestMapping(value = {"/workbench", ""})
public class SideBarController {

    @RequestMapping(value = "/main/index")
    public String mainIndex(){
        return "workbench/main/index";
    }

    @RequestMapping(value = "activity/index")
    public String activityIndex(){
        return "workbench/activity/index";
    }
}
