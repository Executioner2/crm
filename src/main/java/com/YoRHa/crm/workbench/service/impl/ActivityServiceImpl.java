package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.settings.dao.UserDao;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.dao.ActivityDao;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 18:32
 * Versions:1.0.0
 * Description:
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private UserDao userDao;

    @Override
    public List<User> listUser() {

        return activityDao.listUser();
    }

    @Override
    @Transactional
    public Boolean activityAdd(Activity activity) {
        Boolean flag = false;

        User user = userDao.queryUserById(activity.getOwner());
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateBy(user.getName());
        activity.setCreateTime(DateTimeUtil.getSysDate());

        flag = activityDao.activityAdd(activity) == 1 ? true : false;

        return flag;
    }

    @Override
    public Map<String, Object> searchActivityList(Integer pageNo, Integer pageSize, Activity activity) {
        Map<String, Object> map = new HashMap<>();

        List<Activity> activities = activityDao.searchActivityList(activity);

        map.put("activities", activities);
        return map;
    }
}
