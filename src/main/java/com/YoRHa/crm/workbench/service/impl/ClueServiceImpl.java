package com.YoRHa.crm.workbench.service.impl;

import com.YoRHa.crm.utils.DateTimeUtil;
import com.YoRHa.crm.utils.UUIDUtil;
import com.YoRHa.crm.workbench.dao.*;
import com.YoRHa.crm.workbench.domain.*;
import com.YoRHa.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Copyright@1205878539@qq.com
 * Author:2Executioner
 * Date:2021-01-30 16:40
 * Versions:1.0.0
 * Description:
 */

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Override
    public Map<String, Object> searchClueList(Integer pageNo, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        List<Clue> clues = clueDao.listClue();
        map.put("clues", clues);

        return map;
    }

    @Override
    @Transactional
    public Boolean addClue(Clue clue) {
        Boolean flag = false;

        Integer result = clueDao.addClue(clue);

        flag = result == 1 ? true : false;

        return flag;
    }

    @Override
    public Clue queryClueById(String id) {

        return clueDao.queryClueById(id);
    }

    @Override
    public List<Activity> listActivity(String name, String clueId) {

        return activityDao.listActivity(name, clueId);
    }

    @Override
    @Transactional
    public Boolean bundActivity(String cid, String[] aid) {
        Boolean flag = false;
        Integer count = 0;
        ClueActivityRelation c;

        for (String a : aid) {
            c = new ClueActivityRelation();
            c.setId(UUIDUtil.getUUID());
            c.setActivityId(a);
            c.setClueId(cid);
            count += clueActivityRelationDao.bund(c);
        }

        if(count == aid.length){
            flag = true;
        }

        return flag;
    }

    @Override
    public List<Activity> listBundActivity(String clueId) {

        return activityDao.listBundActivity(clueId);
    }

    @Override
    @Transactional
    public Boolean unbundActivity(String id) {
        Boolean flag = false;

        flag = clueDao.unbundActivity(id) == 1 ? true : false;

        return flag;
    }

    @Override
    @Transactional
    public Boolean transaction(Tran tran, String clueId, String createBy) {
        Boolean result = true;
        Integer count = 0;
        String createTime = DateTimeUtil.getSysDate();

        // 1、取得线索
        Clue clue = clueDao.queryClue(clueId);

        // 2、判断是否存在这个customer，因为公司名称唯一所以可以通过公司名称来判断是否存在此客户
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            customer.setAddress(clue.getAddress());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            count = customerDao.insertCustomer(customer);
            if (count != 1){
                result = false;
            }

        }

        // 3、创建联系人
        Contacts contacts = new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        count = contactsDao.insertContacts(contacts);
        if (count != 1){
            result = false;
        }

        // 4、取得线索留言
        List<ClueRemark> clueRemarkList = clueRemarkDao.listClueRemarkByClueId(clueId);
        for (ClueRemark cr : clueRemarkList) {
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setCreateBy(cr.getCreateBy());
            customerRemark.setNoteContent(cr.getNoteContent());
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateTime(createTime);
            count = customerRemarkDao.insertCustomerRemark(customerRemark);
            if (count != 1){
                result = false;
            }

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setNoteContent(cr.getNoteContent());
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setEditBy("0");
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setCreateBy(createBy);
            count = contactsRemarkDao.insertContactsRemark(contactsRemark);
            if (count != 1){
                result = false;
            }

        }

        // 5、添加联系人与活动的联系
        List<Activity> activityList = activityDao.listBundActivity(clueId);
        for (Activity activity : activityList) {
            ContactsActivityRelation caRel = new ContactsActivityRelation();
            caRel.setActivityId(activity.getId());
            caRel.setId(UUIDUtil.getUUID());
            caRel.setContactsId(contacts.getId());
            count = contactsActivityRelationDao.insertCaRel(caRel);
            if (count != 1){
                result = false;
            }

        }

        // 6、判断是否创建交易，不为null就创建
        if (tran != null){
            tran.setSource(clue.getSource());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setCreateTime(createTime);
            tran.setCreateBy(createBy);
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(clue.getOwner());
            count = tranDao.insertTransaction(tran);
            if (count != 1){
                result = false;
            }

            // 7、创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setTranId(tran.getId());
            tranHistory.setStage(clue.getState());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(createTime);
            count = tranHistoryDao.insertTransactionHistory(tranHistory);
            if (count != 1){
                result = false;
            }

        }


        // 8、查询关联的线索留言总条数然后删除线索留言
        Integer countClueRemark = clueRemarkDao.getCountClueRemarkByClueId(clueId);
        count = clueRemarkDao.deleteClueRemarkByClueId(clueId);
        if (countClueRemark != count){
            result = false;
        }

        // 9、删除和线索相关的市场活动
        Integer countCaRel = clueActivityRelationDao.getCountCaRel(clueId);
        count = clueActivityRelationDao.deleteCaRel(clueId);
        if (countCaRel != count){
            result = false;
        }

        // 10、删除线索
        count = clueDao.deleteClueById(clueId);
        if (count != 1){
            result = false;
        }

        return result;
    }
}
