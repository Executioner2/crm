package com.YoRHa.crm.workbench.web.handler.aspect;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 21:41
 * Versions:1.0.0
 * Description:
 */
@Aspect
public class PageAspectHandler {

    @Around("setSearchActivityList() || setSearchClueList() || setSearchTranList()")
    public Object pageAspect(ProceedingJoinPoint pjp) throws Throwable {
        Object[] objects = pjp.getArgs();
        String[] strings = {"activities", "clues", "trans"};
        Integer pageNum = (int)objects[0];
        pageNum = (pageNum-1)*(int)objects[1];

        PageHelper.offsetPage(pageNum, (int)objects[1]);

        Map<String, Object> map = (Map<String, Object>) pjp.proceed();
        List<Object> list = null;
        for (int i = 0; i < strings.length; i++) {
            list = (List<Object>) map.get(strings[i]);
            if(list != null){
                break;
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());

        return map;
    }

    @Pointcut("execution(* com.YoRHa.crm.workbench.service.impl.ActivityServiceImpl.searchActivityList(..))")
    private void setSearchActivityList(){}

    @Pointcut("execution(* com.YoRHa.crm.workbench.service.impl.ClueServiceImpl.searchClueList(..))")
    private void setSearchClueList(){}

    @Pointcut("execution(* com.YoRHa.crm.workbench.service.impl.TranServiceImpl.searchTranList(..))")
    private void setSearchTranList(){}
}
