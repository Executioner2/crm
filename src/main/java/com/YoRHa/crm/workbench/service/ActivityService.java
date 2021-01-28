package com.YoRHa.crm.workbench.service;

import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.workbench.domain.Activity;

import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 18:22
 * Versions:1.0.0
 * Description:
 */
public interface ActivityService {
    List<User> listUser();

    Boolean activityAdd(Activity activity);
}
