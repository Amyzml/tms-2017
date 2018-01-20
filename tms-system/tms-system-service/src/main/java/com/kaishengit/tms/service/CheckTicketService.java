package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.exception.ServiceException;

/**
 * 景区验票业务接口
 * @author NativeBoy
 */
public interface CheckTicketService {


    /**
     * 根据年票的卡号进行验票
     * @param ticketNum 卡号
     */
    void checkTicketByTicketNum(String ticketNum) throws ServiceException;

    /**
     * 根据卡号获取对应的顾客信息
     * @param ticketNum
     * @return
     */
    Customer findCustomerByTicketNum(String ticketNum);

    /**
     * 根据客户id获取客户信息
     * @param id 客户id
     * @return
     */
    Customer findCustomerByCustomerId(Integer id);
}
