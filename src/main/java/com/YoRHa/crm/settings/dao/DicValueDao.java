package com.YoRHa.crm.settings.dao;

import com.YoRHa.crm.settings.domain.DicValue;

import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 15:13
 * Versions:1.0.0
 * Description:
 */
public interface DicValueDao {
    List<DicValue> listDicValueByTypeCode(String typeCode);
}
