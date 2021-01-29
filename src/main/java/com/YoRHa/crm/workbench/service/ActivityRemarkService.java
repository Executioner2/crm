package com.YoRHa.crm.workbench.service;

import com.YoRHa.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-29 16:29
 * Versions:1.0.0
 * Description:
 */
public interface ActivityRemarkService {

    List<ActivityRemark> listActivityRemark(ActivityRemark activityRemark);

    Map<String, Object> addActivityRemark(ActivityRemark activityRemark);

    Boolean deleteActivityRemark(String id);

    String queryActivityNoteContent(String id);

    Map<String, Object> updateActivityRemark(ActivityRemark activityRemark);
}
