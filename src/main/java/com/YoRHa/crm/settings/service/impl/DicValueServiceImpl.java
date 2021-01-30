package com.YoRHa.crm.settings.service.impl;

import com.YoRHa.crm.settings.dao.DicTypeDao;
import com.YoRHa.crm.settings.dao.DicValueDao;
import com.YoRHa.crm.settings.domain.DicType;
import com.YoRHa.crm.settings.service.DicValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 15:29
 * Versions:1.0.0
 * Description:
 */

@Service
public class DicValueServiceImpl implements DicValueService {
    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, Object> listDicValueGroupByType() {
        Map<String, Object> map = new HashMap<>();

        List<DicType> dicTypes = dicTypeDao.listDicType();
        for (DicType dicType : dicTypes) {
            map.put(dicType.getCode() + "List", dicValueDao.listDicValueByTypeCode(dicType.getCode()));
        }

        return map;
    }
}
