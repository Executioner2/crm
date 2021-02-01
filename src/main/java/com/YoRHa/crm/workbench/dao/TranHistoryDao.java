package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    Integer insertTransactionHistory(TranHistory tranHistory);

    List<TranHistory> listTranHistoryByTranId(String tid);
}
