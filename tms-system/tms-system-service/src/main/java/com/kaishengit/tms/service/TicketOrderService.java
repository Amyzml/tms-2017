package com.kaishengit.tms.service;

/**
 * 年票订单业务
 * @author NativeBoy
 */
public interface TicketOrderService {


    /**
     * 新增年票订单记录
     * @param customerId 顾客id
     * @param ticketId 年票id
     * @param storeAccountId 售票站点id
     */
    void saveNewTicketOrder(Integer customerId, Integer ticketId, Integer storeAccountId);

    /**
     * 补办年票订单记录
     * @param ticketId 年票id
     * @param accountId 售票点账户id
     * @param customerId 客户id
     */
    void saveTicketOrderBecauseOldToNew(String ticketId, Integer accountId, Integer customerId);



}
