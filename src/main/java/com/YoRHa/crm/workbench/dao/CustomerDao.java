package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    Integer insertCustomer(Customer customer);
}
