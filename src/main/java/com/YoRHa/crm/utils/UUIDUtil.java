package com.YoRHa.crm.utils;

import java.util.UUID;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 14:37
 * Versions:1.0.0
 * Description:  UUID工具包
 */
public class UUIDUtil {

    private UUIDUtil(){}

    /**
     * @return 返回一个去掉 '-' 的UUID用来当作表中的id主键
     */
    private static String getUUID(){

        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
