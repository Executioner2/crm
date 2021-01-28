package com.YoRHa.crm.settings.domain;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 14:24
 * Versions:1.0.0
 * Description:
 */
public class User {
    private String id;
    private String loginAct;
    private String name;
    private String loginPwd;
    private String email;
    private String expireTime;
    private String lockState;
    private String deptno;
    private String allowIps;
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
