package com.YoRHa.crm.settings.service.impl;

import com.YoRHa.crm.exception.LoginException;
import com.YoRHa.crm.settings.dao.UserDao;
import com.YoRHa.crm.settings.domain.User;
import com.YoRHa.crm.settings.service.UserService;
import com.YoRHa.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 15:23
 * Versions:1.0.0
 * Description:
 */

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User userLogin(User user, String ip) throws LoginException {

        user = userDao.queryUserByNameAndPwd(user);

        System.out.println(user);

        if(user == null){
            throw new LoginException("账号或密码错误！");
        }

        String nowDateTime = DateTimeUtil.getSysDate();
        if(nowDateTime.compareTo(user.getExpireTime()) >= 0){
            throw new LoginException("当前用户已失效，请联系管理员");
        }

        if("0".equals(user.getLockState())) {
            throw new LoginException("当前用户已被锁定，请联系管理员");
        }

        if(!user.getAllowIps().contains(ip)){
            throw new LoginException("非法ip访问");
        }

        return user;
    }

    @Override
    public List<User> listUserName() {
        return userDao.listUserName();
    }
}
