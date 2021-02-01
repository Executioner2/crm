package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    Integer insertCustomer(Customer customer);

    List<String> searchCustomerName(String name);

    Customer getCustomerByFullName(String customerName);
}
