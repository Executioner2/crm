package com.YoRHa.crm.workbench.service;


import com.YoRHa.crm.workbench.domain.Tran;

import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-02-01 15:36
 * Versions:1.0.0
 * Description:
 */

public interface TranService {
    Boolean saveTran(Tran tran);

    Map<String, Object> searchTranList(Integer pageNo, Integer pageSize);

    Tran queryTran(Tran tran);

    Map<String, Object> changeStage(Tran tran, String formerStage);
}
