package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.workbench.dao.CustomerDao;
import com.YoRHa.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-31 18:51
 * Versions:1.0.0
 * Description:
 */

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    @Override
    public List<String> searchCustomerName(String name) {

        return customerDao.searchCustomerName(name);
    }
}
