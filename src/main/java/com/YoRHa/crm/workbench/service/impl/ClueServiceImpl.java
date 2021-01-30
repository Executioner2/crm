package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.dao.ActivityDao;
import com.YoRHa.crm.workbench.dao.ClueActivityRelationDao;
import com.YoRHa.crm.workbench.dao.ClueDao;
import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.Clue;
import com.YoRHa.crm.workbench.domain.ClueActivityRelation;
import com.YoRHa.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 16:40
 * Versions:1.0.0
 * Description:
 */

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;

    @Override
    public Map<String, Object> searchClueList(Integer pageNo, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        List<Clue> clues = clueDao.listClue();
        map.put("clues", clues);

        return map;
    }

    @Override
    @Transactional
    public Boolean addClue(Clue clue) {
        Boolean flag = false;

        Integer result = clueDao.addClue(clue);

        flag = result == 1 ? true : false;

        return flag;
    }

    @Override
    public Clue queryClueById(String id) {

        return clueDao.queryClueById(id);
    }

    @Override
    public List<Activity> listActivity(String name, String clueId) {

        return activityDao.listActivity(name, clueId);
    }

    @Override
    @Transactional
    public Boolean bundActivity(String cid, String[] aid) {
        Boolean flag = false;
        Integer count = 0;
        ClueActivityRelation c;

        for (String a : aid) {
            c = new ClueActivityRelation();
            c.setId(UUIDUtil.getUUID());
            c.setActivityId(a);
            c.setClueId(cid);
            count += clueActivityRelationDao.bund(c);
        }

        if(count == aid.length){
            flag = true;
        }

        return flag;
    }

    @Override
    public List<Activity> listBundActivity(String clueId) {

        return activityDao.listBundActivity(clueId);
    }

    @Override
    @Transactional
    public Boolean unbundActivity(String id) {
        Boolean flag = false;

        flag = clueDao.unbundActivity(id) == 1 ? true : false;

        return flag;
    }
}
