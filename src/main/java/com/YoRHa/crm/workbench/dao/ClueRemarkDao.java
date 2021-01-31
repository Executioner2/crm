package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> listClueRemarkByClueId(String clueId);

    Integer getCountClueRemarkByClueId(String clueId);

    Integer deleteClueRemarkByClueId(String clueId);
}
