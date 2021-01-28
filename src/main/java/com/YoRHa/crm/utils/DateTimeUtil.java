package com.YoRHa.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-28 14:43
 * Versions:1.0.0
 * Description:  日期时间工具包，用来把系统当前日期时间格式化
 */
public class DateTimeUtil {
    private static String getSysDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
