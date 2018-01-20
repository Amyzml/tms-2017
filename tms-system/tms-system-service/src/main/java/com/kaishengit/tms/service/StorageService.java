package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketStore;

import java.util.List;

/**
 *
 * @author NativeBoy
 */
public interface StorageService {

    /**
     * 获得当前仓库年票的最大卡号
     * @return
     */
    String findMaxTicketNum();


    /**
     * 获取当前仓库年票的最大ID
     * @return
     */
    Integer findMaxId();

    /**
     * 年票入库
     * @param integer 入库数量
     * @param beginNum 起始卡号
     */
    void putToStorage(Integer integer, String beginNum);

    /**
     * 根据年票卡号查询年票信息
     * @param ticketNum
     * @return
     */
    Ticket findTicketByTicketNum(String ticketNum);

    /**
     * 作废年票
     * @param ticket 被作废对象
     */
    void setTicketCancellation(Ticket ticket);

    /**
     * 获取已经作废的年票卡号
     * @return
     */
    List<Ticket> findListCancellation();

    /**
     * 获取当前有效的库存量
     * @return
     */
    Long getTicketNumCanUse();

    Integer findMinId();

    String findMinTicketNumById(Integer id);


    /**
     * 年票下发
     * @param id
     * @param ticketNumber
     * @param minCanUseCardNum
     */
    void saveIssueTicket(Integer id, String ticketNumber, String minCanUseCardNum);

    /**
     * 根据站点id获取所属年票列表
     * @return
     */
    List<Ticket> findTicketByStoreId(Integer storeId);
}
