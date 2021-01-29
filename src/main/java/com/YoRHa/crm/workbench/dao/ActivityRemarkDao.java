package com.YoRHa.crm.workbench.dao;

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
}
