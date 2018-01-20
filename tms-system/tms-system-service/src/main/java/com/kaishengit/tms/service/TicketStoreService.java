package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;

import java.util.List;

/**
 * 售票工作站的服务接口
 * @author NativeBoy
 */
public interface TicketStoreService {

    /**
     * 根据id查找售票工作站
     * @param id
     * @return
     */
    TicketStore findById(Integer id);

    /**
     * 查询所有售票站
     * @return
     */
    List<TicketStore> findAll();

    /**
     * 修改售票工作站信息
     * @param ticketStore
     */
    void updateTicketStore(TicketStore ticketStore);

    /**
     * 保存新增售票站点信息
     * @param ticketStore
     */
    void saveNew(TicketStore ticketStore);

    /**
     * 根据站点名称关键字进行查询站点列表
     * @param storeName
     * @return
     */
    List<TicketStore> findTicketStorageByStoreName(String storeName);

    /**
     * 保存新增站点账号
     * @param storeAccount 站点对应的账号
     * @param storeId 站点id
     */
    void saveNewStoreAccount(StoreAccount storeAccount, Integer storeId);
}

