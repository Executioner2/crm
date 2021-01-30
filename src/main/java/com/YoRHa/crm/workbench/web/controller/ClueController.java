package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.settings.service.UserService;
import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.Clue;
import com.YoRHa.crm.workbench.domain.ClueActivityRelation;
import com.YoRHa.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 16:39
 * Versions:1.0.0
 * Description:
 */
@Controller
@RequestMapping(value = "/clue")
public class ClueController {
    @Resource
    private ClueService clueService;
    @Resource
    private UserService userService;
    @Resource
    private ModelAndView mv;

    @RequestMapping(value = "/listUser.do")
    @ResponseBody
    public List<User> listUserName(){

        return userService.listUserName();
    }

    @RequestMapping(value = "/search.do")
    @ResponseBody
    public Map<String, Object> searchClueList(Integer pageNo, Integer pageSize){

        return clueService.searchClueList(pageNo, pageSize);
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addClue(Clue clue, HttpServletRequest request){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User)request.getSession(false).getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysDate());

        return clueService.addClue(clue);
    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView showDetail(String id){

        mv.addObject("clue", clueService.queryClueById(id));
        mv.setViewName("workbench/clue/detail");
        return mv;
    }

    @RequestMapping(value = "/listActivity.do")
    @ResponseBody
    public List<Activity> listActivity(String name, String clueId){

        return clueService.listActivity(name, clueId);
    }

    @RequestMapping(value = "/searchActivity.do")
    @ResponseBody
    public List<Activity> searchActivity(String name, String clueId){

        return clueService.listActivity(name, clueId);
    }

    @RequestMapping(value = "/bund.do")
    @ResponseBody
    public Boolean bundActivity(String cid, String[] aid){

        return clueService.bundActivity(cid, aid);
    }

    @RequestMapping(value = "/list.do")
    @ResponseBody
    public List<Activity> listBundActivity(String clueId){

        return clueService.listBundActivity(clueId);
    }

    @RequestMapping(value = "/unbund.do")
    @ResponseBody
    public Boolean unbundActivity(String id){

        return clueService.unbundActivity(id);
    }

    @RequestMapping(value = "/convert.do")
    public ModelAndView gotoConvertPage(Clue clue){

        mv.addObject("clue", clue);
        mv.setViewName("workbench/clue/convert");
        return mv;
    }
}
