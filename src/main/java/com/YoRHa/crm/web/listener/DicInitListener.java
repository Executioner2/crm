package com.YoRHa.crm.web.listener;

import com.YoRHa.crm.settings.service.DicValueService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        Map<String, Object> map = dicValueService.listDicValueGroupByType();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            List list = (List) map.get(key);
            context.setAttribute(key, list);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
