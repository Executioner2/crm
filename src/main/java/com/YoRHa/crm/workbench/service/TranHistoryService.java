package com.YoRHa.crm.workbench.service;

import com.YoRHa.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-02-01 16:10
 * Versions:1.0.0
 * Description:
 */
public interface TranHistoryService {
    List<TranHistory> listTranHistory(String tid);
}
