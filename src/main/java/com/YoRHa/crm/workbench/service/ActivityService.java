package com.YoRHa.crm.workbench.service;

import com.YoRHa.crm.exception.SqlDataDeleteException;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 18:22
 * Versions:1.0.0
 * Description:
 */
public interface ActivityService {
    Boolean activityAdd(Activity activity);

    Map<String, Object> searchActivityList(Integer pageNo, Integer pageSize, Activity activity);

    Boolean activityDeleteById(String[] id) throws SqlDataDeleteException;

    Map<String, Object> queryActivityById(Activity activity);

    Boolean updateActivity(Activity activity);

    Activity queryActivityOnRemarkPage(Activity activity);

    List<Activity> listActivity();
}
