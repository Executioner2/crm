package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.workbench.dao.ContactsDao;
import com.YoRHa.crm.workbench.domain.Contacts;
import com.YoRHa.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-31 20:19
 * Versions:1.0.0
 * Description:
 */

@Service
public class ContactsServiceImpl implements ContactsService {
    @Resource
    private ContactsDao contactsDao;

    @Override
    public List<Contacts> listContacts() {

        return contactsDao.searchContacts(null);
    }
}
