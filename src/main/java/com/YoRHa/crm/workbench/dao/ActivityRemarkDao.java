package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 18:34
 * Versions:1.0.0
 * Description:
 */
public interface ActivityRemarkDao {
    Integer countActivityRemarkByDelete(String[] id);

    Integer deleteActivityRemarkByActivityId(String[] id);

    List<ActivityRemark> listActivityRemark(ActivityRemark activityRemark);

    Integer addActivityRemark(ActivityRemark activityRemark);

    Integer deleteActivityRemarkById(String id);

    String queryActivityNoteContent(String id);

    Integer updateActivityRemark(ActivityRemark activityRemark);
}
