package com.YoRHa.crm.web.listener;

import com.YoRHa.crm.settings.service.DicValueService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 15:07
 * Versions:1.0.0
 * Description:  数据字典初始化
 */
public class DicInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        //取得application容器中的bean
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        DicValueService dicValueService = (DicValueService) ac.getBean("dicValueServiceImpl");

        //获得字典数据
        Map<String, Object> map = dicValueService.listDicValueGroupByType();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            List list = (List) map.get(key);
            context.setAttribute(key, list);
        }

        //获得Stage2Possibility.properties文件中的值
        ResourceBundle bundle = ResourceBundle.getBundle("/conf/stage2Possibility");
        Enumeration<String> keys = bundle.getKeys();
        Map<String, String> eMap = new HashMap<>();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            eMap.put(key, bundle.getString(key));
        }
        context.setAttribute("stage2Possibility", eMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
