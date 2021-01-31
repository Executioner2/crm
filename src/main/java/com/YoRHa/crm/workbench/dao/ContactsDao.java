package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Activity;
import com.YoRHa.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    Integer insertContacts(Contacts contacts);

    List<Contacts> searchContacts(String name);
}
