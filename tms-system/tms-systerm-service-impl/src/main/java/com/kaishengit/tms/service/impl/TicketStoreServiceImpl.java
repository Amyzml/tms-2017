package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.entity.TicketStoreExample;
import com.kaishengit.tms.service.TicketStoreService;
import com.kaishengit.tms.service.mapper.StoreAccountMapper;
import com.kaishengit.tms.service.mapper.TicketStoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 售票工作站点
 * @author NativeBoy
 */
@Service
public class TicketStoreServiceImpl implements TicketStoreService {


    private Logger logger = LoggerFactory.getLogger(TicketStoreServiceImpl.class);
    @Autowired
    private TicketStoreMapper ticketStoreMapper;
    @Autowired
    private StoreAccountMapper storeAccountMapper;
    /**
     * 根据id查找售票工作站
     *
     * @param id
     * @return
     */
    @Override
    public TicketStore findById(Integer id) {
        return ticketStoreMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有售票站
     *
     * @return
     */
    @Override
    public List<TicketStore> findAll() {
        return ticketStoreMapper.selectByExample(new TicketStoreExample());
    }

    /**
     * 修改售票工作站信息
     * @param ticketStore
     */
    @Override
    public void updateTicketStore(TicketStore ticketStore) {
        ticketStoreMapper.updateByPrimaryKey(ticketStore);
    }

    /**
     * 保存新增售票站点信息
     *
     * @param ticketStore
     */
    @Override
    public void saveNew(TicketStore ticketStore) {
        ticketStoreMapper.insertSelective(ticketStore);
        logger.info("{} 新增售票站点信息" + ticketStore.getId());
    }

    /**
     * 根据站点名称关键字进行查询站点列表
     * @param storeName
     * @return
     */
    @Override
    public List<TicketStore> findTicketStorageByStoreName(String storeName) {
        List<TicketStore> ticketStoreList = ticketStoreMapper.findTicketStoreByName(storeName);
        return ticketStoreList;
    }

    /**
     * 保存新增站点账号
     *
     * @param storeAccount 站点对应的账号
     * @param storeId      站点id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewStoreAccount(StoreAccount storeAccount, Integer storeId) {
        storeAccount.setCreateTime(new Date());
        storeAccount.setStoreState("正常");
        storeAccount.setUpdateTime(new Date());
        storeAccount.setTicketStoreId(storeId);
        storeAccountMapper.insertSelective(storeAccount);
        //绑定站点与站点账号
        TicketStore ticketStore = ticketStoreMapper.selectByPrimaryKey(storeId);
        ticketStore.setStoreAccountId(storeAccount.getId());
        ticketStoreMapper.updateByPrimaryKeySelective(ticketStore);

        logger.info("{}新增站点账户>>>" + storeAccount.getStoreAccount());
    }
}
