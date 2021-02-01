package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.settings.service.UserService;
import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.Contacts;
import com.YoRHa.crm.workbench.domain.Tran;
import com.YoRHa.crm.workbench.domain.TranHistory;
import com.YoRHa.crm.workbench.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Resource
    private TranService tranService;
    @Resource
    private TranHistoryService tranHistoryService;

    @RequestMapping(value = "/savePage.do")
    public ModelAndView gotoSavePage() {

        List<User> users = userService.listUserName();
        mv.addObject("users", users);
        mv.setViewName("workbench/transaction/save");

        return mv;
    }

    @RequestMapping(value = "/searchName.do")
    @ResponseBody
    public List<String> searchCustomerName(String name) {

        List<String> names = customerService.searchCustomerName(name);

        return names;
    }

    @RequestMapping(value = "/listActivity.do")
    @ResponseBody
    public List<Activity> searchActivity() {

        return activityService.listActivity();
    }

    @RequestMapping(value = "/listContacts.do")
    @ResponseBody
    public List<Contacts> listContacts() {

        return contactsService.listContacts();
    }

    @RequestMapping(value = "/save.do")
    public String saveTran(Tran tran, HttpServletRequest request) {

        tran.setCreateBy(((User) request.getSession(false).getAttribute("user")).getName());
        tran.setCreateTime(DateTimeUtil.getSysDate());
        tran.setId(UUIDUtil.getUUID());

        tranService.saveTran(tran);

        return "redirect:/workbench/transaction/index.do";
    }

    @RequestMapping(value = "/listTran.do")
    @ResponseBody
    public Map<String, Object> listTran(Integer pageNo, Integer pageSize) {

        return tranService.searchTranList(pageNo, pageSize);
    }

    @RequestMapping(value = "/detailPage.do")
    public ModelAndView detailPage(Tran tran, HttpServletRequest request) {

        tran = tranService.queryTran(tran);
        ServletContext context = request.getServletContext();
        String stage = tran.getStage();
        Map<String, String> map = (Map<String, String>) context.getAttribute("stage2Possibility");
        tran.setPossibility(map.get(stage));

        mv.addObject("tran", tran);
        mv.setViewName("workbench/transaction/detail");
        return mv;
    }

    @RequestMapping(value = "/listTranHistory.do")
    @ResponseBody
    public List<TranHistory> listTranHistory(String tid, HttpServletRequest request) {
        ServletContext context = request.getServletContext();

        List<TranHistory> thList = tranHistoryService.listTranHistory(tid);
        Map<String, String> map = (Map<String, String>) context.getAttribute("stage2Possibility");
        for (TranHistory th : thList) {
            String possibility = map.get(th.getStage());
            th.setPossibility(possibility);
        }

        return thList;
    }

    @RequestMapping(value = "/changeStage.do")
    @ResponseBody
    public Map<String, Object> changeStage(Tran tran, String formerStage, HttpServletRequest request) {
        tran.setEditBy(((User)request.getSession(false).getAttribute("user")).getName());
        tran.setEditTime(DateTimeUtil.getSysDate());
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("stage2Possibility");
        tran.setPossibility(map.get(tran.getStage()));

        return tranService.changeStage(tran, formerStage);
    }

}
