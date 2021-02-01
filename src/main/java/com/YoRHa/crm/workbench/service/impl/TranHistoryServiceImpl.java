package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.workbench.dao.TranHistoryDao;
import com.YoRHa.crm.workbench.domain.TranHistory;
import com.YoRHa.crm.workbench.service.TranHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-02-01 16:10
 * Versions:1.0.0
 * Description:
 */
@Service
public class TranHistoryServiceImpl implements TranHistoryService {
    @Resource
    private TranHistoryDao tranHistoryDao;

    @Override
    public List<TranHistory> listTranHistory(String tid) {

        return tranHistoryDao.listTranHistoryByTranId(tid);
    }
}
