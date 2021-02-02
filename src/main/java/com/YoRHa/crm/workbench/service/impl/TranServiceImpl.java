package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.dao.CustomerDao;
import com.YoRHa.crm.workbench.dao.TranDao;
import com.YoRHa.crm.workbench.dao.TranHistoryDao;
import com.YoRHa.crm.workbench.domain.Customer;
import com.YoRHa.crm.workbench.domain.Tran;
import com.YoRHa.crm.workbench.domain.TranHistory;
import com.YoRHa.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-02-01 15:37
 * Versions:1.0.0
 * Description:
 */
@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao tranDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private TranHistoryDao tranHistoryDao;

    @Override
    @Transactional
    public Boolean saveTran(Tran tran) {
        Boolean flag = true;
        String customerName = tran.getCustomerId();

        // 1、检查有有无客户，没有就创建客户，有就取出来
        Customer customer = customerDao.getCustomerByFullName(customerName);
        if (customer == null){
            customer = new Customer();
            customer.setDescription(tran.getDescription());
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setName(customerName);
            customer.setContactSummary(tran.getContactSummary());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysDate());
            Integer count1 = customerDao.insertCustomer(customer);
            if (count1 != 1){
                flag = false;
            }

        }

        // 2、存入交易
        tran.setCustomerId(customer.getId());
        Integer count2 = tranDao.insertTransaction(tran);
        if (count2 != 1){
            flag = false;
        }

        // 3、创建交易历史
        TranHistory th = new TranHistory();
        th.setCreateTime(DateTimeUtil.getSysDate());
        th.setCreateBy(tran.getCreateBy());
        th.setTranId(tran.getId());
        th.setStage(tran.getStage());
        th.setMoney(tran.getMoney());
        th.setId(UUIDUtil.getUUID());
        th.setExpectedDate(tran.getExpectedDate());
        Integer count3 = tranHistoryDao.insertTransactionHistory(th);
        if (count3 != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> searchTranList(Integer pageNo, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        List<Tran> trans = tranDao.listTran();
        map.put("trans", trans);

        return map;
    }

    @Override
    public Tran queryTran(Tran tran) {

        return tranDao.queryTran(tran);
    }

    @Override
    @Transactional
    public Map<String, Object> changeStage(Tran tran, String formerStage) {
        Map<String, Object> map = new HashMap<>();
        Boolean flag = true;

        // 1、创建交易历史
        TranHistory th = new TranHistory();
        th.setCreateTime(DateTimeUtil.getSysDate());
        th.setCreateBy(tran.getEditBy());
        th.setTranId(tran.getId());
        th.setStage(formerStage);
        th.setMoney(tran.getMoney());
        th.setId(tran.getEditTime());
        th.setExpectedDate(tran.getExpectedDate());
        Integer count1 = tranHistoryDao.insertTransactionHistory(th);
        if (count1 != 1){
            flag = false;
        }

        // 2、更新交易
        Integer count2 = tranDao.changeStage(tran);
        if (count2 != 1){
            flag = false;
        }

        map.put("result", flag);
        map.put("tran", tran);
        return map;
    }

    @Override
    public Map<String, Object> getTranChart() {
        Map<String, Object> map = new HashMap<>();

        // 1、获得total
        Integer total = tranDao.countTotal();

        // 2、获得stage和分组的count(*)
        List<Map<String, String>> mapList = tranDao.countGroupByStage();

        map.put("total", total);
        map.put("list", mapList);
        return map;
    }
}