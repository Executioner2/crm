package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.ActivityRemark;
import com.YoRHa.crm.workbench.service.ActivityRemarkService;
import com.YoRHa.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-29 16:28
 * Versions:1.0.0
 * Description:
 */
@Controller
@RequestMapping(value = "/activityRemark")
public class ActivityRemarkController {
    @Resource
    private ActivityService activityService;
    @Resource
    private ActivityRemarkService activityRemarkService;
    @Resource
    private ModelAndView mv;

    @RequestMapping(value = "/show.do")
    public ModelAndView showRemark(Activity activity){

        activity = activityService.queryActivityOnRemarkPage(activity);

        mv.addObject("activity", activity);
        mv.setViewName("workbench/activity/detail");

        return mv;
    }

    @RequestMapping(value = "/list.do")
    @ResponseBody
    public List<ActivityRemark> listActivityRemark(ActivityRemark activityRemark){

        return activityRemarkService.listActivityRemark(activityRemark);
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addActivityRemark(ActivityRemark activityRemark, HttpServletRequest request){
        String createBy = ((User)request.getSession(false).getAttribute("user")).getName();

        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysDate());
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag("0");

        return activityRemarkService.addActivityRemark(activityRemark);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteActivityRemark(String id){

        return activityRemarkService.deleteActivityRemark(id);
    }

    @RequestMapping(value = "/query.do")
    @ResponseBody
    public String queryActivityNoteContent(String id){

        return activityRemarkService.queryActivityNoteContent(id);
    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map<String, Object> updateActivityRemark(ActivityRemark activityRemark, HttpServletRequest request){
        String editBy = ((User)request.getSession(false).getAttribute("user")).getName();

        activityRemark.setEditFlag("1");
        activityRemark.setEditBy(editBy);
        activityRemark.setEditTime(DateTimeUtil.getSysDate());

        return activityRemarkService.updateActivityRemark(activityRemark);
    }


}
