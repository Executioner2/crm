package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.exception.SqlDataDeleteException;
import com.YoRHa.crm.settings.dao.UserDao;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.dao.ActivityDao;
import com.YoRHa.crm.workbench.dao.ActivityRemarkDao;
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
    private ActivityRemarkDao activityRemarkDao;
    @Resource
    private UserDao userDao;

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

    @Override
    @Transactional
    public Boolean activityDeleteById(String[] id) throws SqlDataDeleteException {
        Boolean flag = false;

        //统计要删除的activityRemark数量
        Integer count = activityRemarkDao.countActivityRemarkByDelete(id);
        //删除activityRemark
        Integer deleteCount = activityRemarkDao.deleteActivityRemarkByActivityId(id);

        //删除条数和要删除的条数相等就开始删除市场活动
        if(count == deleteCount){

            deleteCount = activityDao.deleteActivityById(id);

            if(deleteCount == id.length){

                flag = true;

            }else{
                throw new SqlDataDeleteException("删除失败，数据删除条数与期望删除条数不符");
            }

        //否则抛出异常
        }else{
            throw new SqlDataDeleteException("删除失败，数据删除条数与期望删除条数不符");
        }

        return flag;
    }

    @Override
    public Map<String, Object> queryActivityById(Activity activity) {
        Map<String, Object> map = new HashMap<>();

        List<User> users = userDao.listUserName();
        activity = activityDao.queryActivityById(activity);

        map.put("users", users);
        map.put("activity", activity);

        return map;
    }

    @Override
    @Transactional
    public Boolean updateActivity(Activity activity) {
        Boolean flag = false;

        flag = activityDao.updateActivity(activity) == 1 ? true : false;

        return flag;
    }

    @Override
    public Activity queryActivityOnRemarkPage(Activity activity) {


        return activityDao.queryActivity(activity);
    }

    @Override
    public List<Activity> listActivity() {

        return activityDao.listActivity(null, null);
    }
}
