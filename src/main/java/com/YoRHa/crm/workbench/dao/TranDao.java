package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    Integer insertTransaction(Tran tran);

    List<Tran> listTran();

    Tran queryTran(Tran tran);

    Integer changeStage(Tran tran);
}
