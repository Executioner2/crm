package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    Integer insertTransaction(Tran tran);

    List<Tran> listTran();

    Tran queryTran(Tran tran);

    Integer changeStage(Tran tran);

    Integer countTotal();

    List<Map<String, String>> countGroupByStage();
}
