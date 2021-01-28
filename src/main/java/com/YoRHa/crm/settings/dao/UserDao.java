package com.YoRHa.crm.settings.dao;

import com.YoRHa.crm.settings.domain.User;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 15:01
 * Versions:1.0.0
 * Description:
 */
public interface UserDao {
    User queryUserByNameAndPwd(User user);
}
