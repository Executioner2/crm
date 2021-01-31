package com.YoRHa.crm.workbench.dao;

import com.YoRHa.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {

    List<Clue> listClue();

    Integer addClue(Clue clue);

    Clue queryClueById(String id);

    Integer unbundActivity(String id);

    Clue queryClue(String clueId);

    Integer deleteClueById(String clueId);
}
