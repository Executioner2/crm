package com.YoRHa.crm.settings.service;

import com.YoRHa.crm.exception.LoginException;
import com.YoRHa.crm.settings.domain.User;

import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 15:05
 * Versions:1.0.0
 * Description:
 */
public interface UserService {

    User userLogin(User user, String ip) throws LoginException;
    List<User> listUserName();
}
