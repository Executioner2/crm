package com.YoRHa.crm.settings.domain;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 14:24
 * Versions:1.0.0
 * Description:
 */
public class User {
    private String id; // id主键
    private String loginAct; //  登录账号
    private String name; //  真实姓名
    private String loginPwd; //  登录密码
    private String email; //  邮箱
    private String expireTime; //  失效时间（为null表示永不失效，否则在该时间之前有效）
    private String lockState; //  账户是否被锁 0锁定，1未被锁定
    private String deptno; //      部门编号
    private String allowIps; //     允许访问的ip
    private String createTime; //   创建时间
    private String createBy; // 创建人
    private String editTime; //  编辑时间
    private String editBy; //  编辑人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
