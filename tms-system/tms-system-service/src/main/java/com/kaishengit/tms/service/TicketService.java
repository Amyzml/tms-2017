package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.exception.ServiceException;

import java.util.List;

/**
 * 有关年票的业务处理层
 * @author NativeBoy
 */
public interface TicketService {


    /**
     * 保存办理年票
     * @param customer 顾客
     */
    void saveCustomer(Customer customer);

    /**
     * 根据主键查询顾客信息
     * @param id 顾客id
     * @return
     */
    Customer findCustomerById(Integer id);

    /**
     * 根据顾客id查询对应的年票信息
     * @param id
     * @return
     */
    Ticket findTicketByCustomerId(Integer id);

    /**
     * 根据年票id查询顾客信息
     * @param ticketId
     * @return
     */
    Customer findCustomerByTicketId(Integer ticketId);

    /**
     * 根据站点id查询本站已售年票列表
     * @param id 站点id
     * @return
     */
    List<Ticket> findAllTicketLocalSall(Integer id);

    /**
     * 根据卡号获取年票信息
     * @param ticketNum
     * @return
     */
    Ticket findTicketByTicketNum(String ticketNum);

    /**
     * 续费 默认周期为一年
     * @param ticketId 年票卡号
     */
    void saveRenewWithTicketId(Integer ticketId);

    /**
     * 根据身份证号码获取顾客信息
     * @param iDcard
     * @return
     */
    Customer findCustomerByIDcard(String iDcard);

    /**
     * 顾客挂失,更新年票信息
     * @param ticketId 年票id
     */
    void updateTicketBecauseCardLoss(Integer ticketId);
    /**
     * 顾客解卦,更新年票信息
     * @param ticketId 年票id
     */
    void updateTicketBecauseCardFind(Integer ticketId);

    /**
     * 对于已经挂失的年票进行 补办
     * @param ticketId 年票卡号
     */
    void reapplyTicketBecauseCardLoss(Integer ticketId) throws ServiceException;

    /**
     * 补办新年票
     * @param oldTicketId 老卡id
     * @param ticketId 新卡id
     */
    void saveOldCardCoppyToNewTicket(Integer oldTicketId, String ticketId);
}
