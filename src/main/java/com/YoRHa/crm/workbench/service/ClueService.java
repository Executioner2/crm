package com.YoRHa.crm.workbench.service;

import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.Clue;
import com.YoRHa.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 16:40
 * Versions:1.0.0
 * Description:
 */
public interface ClueService {

    Map<String, Object> searchClueList(Integer pageNo, Integer pageSize);

    Boolean addClue(Clue clue);

    Clue queryClueById(String id);

    List<Activity> listActivity(String name, String clueId);

    Boolean bundActivity(String cid, String[] aid);

    List<Activity> listBundActivity(String clueId);

    Boolean unbundActivity(String id);
}
