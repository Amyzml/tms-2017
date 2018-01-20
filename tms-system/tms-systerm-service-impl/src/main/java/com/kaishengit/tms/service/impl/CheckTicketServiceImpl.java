package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.CheckTicketService;
import com.kaishengit.tms.service.mapper.CustomerMapper;
import com.kaishengit.tms.service.mapper.TicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 景区验票业务处理
 * @author NativeBoy
 */
@Service
public class CheckTicketServiceImpl implements CheckTicketService {

    private Logger logger = LoggerFactory.getLogger(CheckTicketServiceImpl.class);
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 根据年票的卡号进行验票
     * @param ticketNum 卡号
     */
    @Override
    public void checkTicketByTicketNum(String ticketNum) throws ServiceException{
        /*List<Ticket> ticketList = ticketMapper.findTicketByTicketNum(ticketNum);
        if(ticketList == null || ticketList.isEmpty()){

        }*/
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);
        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);

        if(ticketList == null || ticketList.isEmpty()){
            throw new ServiceException("输入错误");
        }
       Ticket ticket = ticketList.get(0);
        if(ticket == null){
            throw new ServiceException("卡号错误");
        }

        if(!"正常".equals(ticket.getTicketState())){
            throw new ServiceException("该年票暂不可用");
        }
        //TODO 是否已经用过一次,同一天内
        //判断有效日期是否在当前时间之前,true:不可用 false:年票可用
        if(ticket.getTicketValidityEnd().before(new Date())){
            throw new ServiceException("不在有效期");
        }
        logger.info("{}验票通过" + ticketNum);
    }

    /**
     * 根据卡号获取对应的顾客信息
     * @param ticketNum
     * @return 顾客
     */
    @Override
    public Customer findCustomerByTicketNum(String ticketNum) {
        /*List<Ticket> ticketList = ticketMapper.findTicketByTicketNum(ticketNum);
        if(ticketList.isEmpty() || ticketList == null){

        }*/
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);
        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);
        if(ticketList.isEmpty() || ticketList == null){
            throw new ServiceException("输入有误");
        }
        Ticket ticket = ticketList.get(0);
        return customerMapper.findCustomerByTicketId(ticket.getId());
    }

    /**
     * 根据客户id获取客户信息
     * @param id 客户id
     * @return
     */
    @Override
    public Customer findCustomerByCustomerId(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }
}
