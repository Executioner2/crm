package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.settings.dao.UserDao;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.settings.service.UserService;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.Contacts;
import com.YoRHa.crm.workbench.service.ActivityService;
import com.YoRHa.crm.workbench.service.ContactsService;
import com.YoRHa.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-31 18:14
 * Versions:1.0.0
 * Description:
 */

@Controller
@RequestMapping(value = "/tran")
public class TranController {
    @Resource
    private ModelAndView mv;
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ContactsService contactsService;

    @RequestMapping(value = "/savePage.do")
    public ModelAndView gotoSavePage(){

        List<User> users = userService.listUserName();
        mv.addObject("users", users);
        mv.setViewName("workbench/transaction/save");

        return mv;
    }

    @RequestMapping(value = "/searchName.do")
    @ResponseBody
    public List<String> searchCustomerName(String name){

        List<String> names = customerService.searchCustomerName(name);

        return names;
    }

    @RequestMapping(value = "/listActivity.do")
    @ResponseBody
    public List<Activity> searchActivity(){

        return activityService.listActivity();
    }

    @RequestMapping(value = "/listContacts.do")
    @ResponseBody
    public List<Contacts> listContacts(){

        return contactsService.listContacts();
    }



}
