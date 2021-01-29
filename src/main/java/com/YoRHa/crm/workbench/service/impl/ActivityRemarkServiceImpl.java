package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.workbench.dao.ActivityRemarkDao;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.ActivityRemark;
import com.YoRHa.crm.workbench.service.ActivityRemarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-29 16:36
 * Versions:1.0.0
 * Description:
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Resource
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public List<ActivityRemark> listActivityRemark(ActivityRemark activityRemark) {

        return activityRemarkDao.listActivityRemark(activityRemark);
    }

    @Override
    @Transactional
    public Map<String, Object> addActivityRemark(ActivityRemark activityRemark) {
        Boolean flag = false;
        Map<String, Object> map = new HashMap<>();

        flag = activityRemarkDao.addActivityRemark(activityRemark) == 1 ? true : false;

        map.put("result", flag);
        map.put("remark", activityRemark);
        return map;
    }

    @Override
    @Transactional
    public Boolean deleteActivityRemark(String id) {
        Boolean flag = false;

        flag = activityRemarkDao.deleteActivityRemarkById(id) == 1 ? true : false;

        return flag;
    }

    @Override
    public String queryActivityNoteContent(String id) {

        return activityRemarkDao.queryActivityNoteContent(id);
    }

    @Override
    public Map<String, Object> updateActivityRemark(ActivityRemark activityRemark) {
        Map<String, Object> map = new HashMap<>();
        Boolean flag = false;

        flag = activityRemarkDao.updateActivityRemark(activityRemark) == 1 ? true : false;

        map.put("result", flag);
        map.put("remark", activityRemark);

        return map;
    }
}
