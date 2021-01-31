package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {

    Integer bund(ClueActivityRelation clueActivityRelation);

    Integer getCountCaRel(String clueId);

    Integer deleteCaRel(String clueId);
}
