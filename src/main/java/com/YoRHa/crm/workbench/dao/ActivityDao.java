package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 18:24
 * Versions:1.0.0
 * Description:
 */
public interface ActivityDao {
    Integer activityAdd(Activity activity);

    List<Activity> searchActivityList(Activity activity);

    Integer deleteActivityById(String[] id);

    Activity queryActivityById(Activity activity);

    Integer updateActivity(Activity activity);

    Activity queryActivity(Activity activity);

    List<Activity> listActivity(@Param("name") String name, @Param("clueId") String clueId);

    List<Activity> listBundActivity(String clueId);
}
