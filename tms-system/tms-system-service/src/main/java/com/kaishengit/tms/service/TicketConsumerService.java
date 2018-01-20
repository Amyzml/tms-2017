package com.kaishengit.tms.service;

/**
 * 景区刷卡验票业务接口
 * @author NativeBoy
 */
public interface TicketConsumerService {

    /**
     * 验票通过,新增一条消费记录
     * @param ticketId 年票id
     * @param scenicAccountId 景区账号id
     */
    void saveNewConsumerOrder(Integer ticketId, Integer scenicAccountId);
}
