package com.YoRHa.crm.workbench.web.controller;

import com.YoRHa.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-02-02 15:06
 * Versions:1.0.0
 * Description:
 */

@Controller
@RequestMapping(value = "/echarts")
public class ChartController {
    @Resource
    private TranService tranService;

    @RequestMapping(value = "/tranChart.do")
    @ResponseBody
    public Map<String, Object> getTranChart(){

        return tranService.getTranChart();
    }
}
