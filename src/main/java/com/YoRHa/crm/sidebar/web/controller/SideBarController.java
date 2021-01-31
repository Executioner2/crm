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

    @RequestMapping(value = "/main/index.do")
    public String mainIndex(){
        return "workbench/main/index";
    }

    @RequestMapping(value = "/activity/index.do")
    public String activityIndex(){
        return "workbench/activity/index";
    }

    @RequestMapping(value = "/clue/index.do")
    public String clueIndex(){
        return "workbench/clue/index";
    }

    @RequestMapping(value = "/customer/index.do")
    public String customerIndex(){
        return "workbench/customer/index";
    }

    @RequestMapping(value = "/contacts/index.do")
    public String contactsIndex(){
        return "workbench/contacts/index";
    }

    @RequestMapping(value = "/transaction/index.do")
    public String transactionIndex(){
        return "workbench/transaction/index";
    }
}
