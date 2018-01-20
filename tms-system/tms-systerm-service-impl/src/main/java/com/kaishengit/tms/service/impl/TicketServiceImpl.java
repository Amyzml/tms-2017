package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.CustomerExample;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.TicketService;
import com.kaishengit.tms.service.mapper.CustomerMapper;
import com.kaishengit.tms.service.mapper.TicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 售票系统业务 顾客信息处理, 年票信息处理
 * @author NativeBoy
 */
@Service
public class TicketServiceImpl implements TicketService {

    private Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TicketMapper ticketMapper;

    /**
     * 保存办理年票
     *TODO
     * @param customer 顾客
     */
    @Override
    public void saveCustomer(Customer customer) {
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customerMapper.insertSelective(customer);

        Ticket ticket = ticketMapper.selectByPrimaryKey(customer.getTicketId());
        if(ticket == null){
            throw new ServiceException("年票不存在");
        }
        ticket.setUpdateTime(new Date());
        ticket.setCustomerId(customer.getId());
        ticket.setTicketValidityEnd(getOutTime());
        ticket.setTicketValidityStart(new Date());
        ticket.setTicketState("正常");
        ticket.setCreateTime(new Date());
        ticketMapper.updateByPrimaryKeySelective(ticket);
        logger.info("{}办理年票成功");
    }

    /**
     * 根据主键查询顾客信息
     * @param id 顾客id
     * @return
     */
    @Override
    public Customer findCustomerById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据顾客id查询对应的年票信息
     * @param id
     * @return
     */
    @Override
    public Ticket findTicketByCustomerId(Integer id) {
        return ticketMapper.findTicketByCustomerId(id);
    }

    /**
     * 根据年票id查询顾客信息
     *
     * @param ticketId
     * @return
     */
    @Override
    public Customer findCustomerByTicketId(Integer ticketId) {
        return customerMapper.findCustomerByTicketId(ticketId);
    }

    /**
     * 根据站点id查询本站已售年票列表
     * @param id 站点id
     * @return
     */
    @Override
    public List<Ticket> findAllTicketLocalSall(Integer id) {
        return ticketMapper.findAllTicketLocalSall(id);
    }

    /**
     * 根据卡号获取年票信息
     *
     * @param ticketNum
     * @return
     */
    @Override
    public Ticket findTicketByTicketNum(String ticketNum) {
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);
        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);
        if(ticketList != null && !ticketList.isEmpty()){
            return ticketList.get(0);
        }
        return null;
    }

    /**
     * 续费 默认周期为一年
     * @param ticketId 年票卡号
     */
    @Override
    public void saveRenewWithTicketId(Integer ticketId) {
        Ticket ticket = ticketMapper.selectByPrimaryKey(ticketId);
        if(ticket == null){
            throw new ServiceException("年票不存在");
        }
        if(ticket.getTicketValidityEnd() == null){
            throw new ServiceException("无效的年票");
        }
        ticket.setTicketValidityEnd(getRenewAfterTime(ticket.getTicketValidityEnd(),365));
        ticket.setUpdateTime(new Date());
        ticketMapper.updateByPrimaryKeySelective(ticket);
        logger.info("{}续费一年成功" + ticket.getTicketNum());
    }

    /**
     * 根据身份证号码获取顾客信息
     *
     * @param iDcard
     * @return
     */
    @Override
    public Customer findCustomerByIDcard(String iDcard) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andCustomerIdCardEqualTo(iDcard);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        if(customerList != null && !customerList.isEmpty()){
            return customerList.get(0);
        }
        return null;
    }

    /**
     * 顾客挂失,更新年票信息
     *
     * @param ticketId 年票id
     */
    @Override
    public void updateTicketBecauseCardLoss(Integer ticketId) {
        Ticket ticket = ticketMapper.selectByPrimaryKey(ticketId);
        if(ticket == null){
            throw new ServiceException("该年票不存在");
        }
        if(!ticket.getTicketState().equals("正常")){
            throw new ServiceException("该年票状态不正常,不可以挂失!");
        }
        ticket.setUpdateTime(new Date());
        ticket.setTicketState("挂失");
        ticketMapper.updateByPrimaryKeySelective(ticket);
        logger.info("{}挂失成功" + ticketId);
    }

    /**
     * 顾客解卦,更新年票信息
     *
     * @param ticketId 年票id
     */
    @Override
    public void updateTicketBecauseCardFind(Integer ticketId) {
        Ticket ticket = ticketMapper.selectByPrimaryKey(ticketId);
        if(ticket == null){
            throw new ServiceException("该年票不存在");
        }
        if(!ticket.getTicketState().equals("挂失")){
            throw new ServiceException("该年票不在挂失状态");
        }
        ticket.setUpdateTime(new Date());
        ticket.setTicketState("正常");
        ticketMapper.updateByPrimaryKeySelective(ticket);
        logger.info("{}解挂成功" + ticketId);
    }

    /**
     * 对于已经挂失的年票进行 补办
     *
     * @param ticketId 年票卡号
     */
    @Override
    public void reapplyTicketBecauseCardLoss(Integer ticketId) throws ServiceException {
        Ticket ticket = ticketMapper.selectByPrimaryKey(ticketId);
        if(ticket == null){
            throw new ServiceException("年票不存在");
        }
        if(!ticket.getTicketState().equals("挂失")){
            throw new ServiceException("该年票不可补办");
        }
    }

    /**
     * 补办新年票
     * 更新客户信息(年票对应的id)
     *老卡信息删除,只保留  卡号,年票状态,所属站点id 字段,
     * @param oldTicketId 老卡id
     * @param ticketId    新卡id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOldCardCoppyToNewTicket(Integer oldTicketId, String ticketId) {

        Ticket ticket = ticketMapper.selectByPrimaryKey(oldTicketId);
        Customer customer = customerMapper.findCustomerByTicketId(oldTicketId);
        if(ticket == null || customer == null){
            throw new ServiceException("错误的年票id");
        }

        customer.setTicketId(Integer.valueOf(ticketId));
        customerMapper.updateByPrimaryKeySelective(customer);

        Ticket ticketNew = ticketMapper.selectByPrimaryKey(Integer.valueOf(ticketId));

        ticketNew.setTicketState("正常");
        ticketNew.setTicketValidityEnd(ticket.getTicketValidityEnd());
        ticketNew.setCreateTime(new Date());
        ticketNew.setUpdateTime(new Date());
        ticketNew.setTicketValidityStart(ticket.getTicketValidityStart());
        ticketNew.setCustomerId(ticket.getCustomerId());
        ticketNew.setStoreAccountId(ticket.getStoreAccountId());
        ticketMapper.updateByPrimaryKeySelective(ticketNew);
        //舍弃部分信息,只保留能够用于统计和财务计算的字段
        /*Ticket ticket1 = new Ticket();
        ticket1.setId(ticket.getId());
        ticket1.setTicketNum(ticket.getTicketNum());
        ticket1.setTicketState("已补办");
        ticket1.setStoreAccountId(ticket.getStoreAccountId());*/
        ticket.setTicketState("已补办");
        ticket.setCustomerId(0);

        ticketMapper.updateByPrimaryKeySelective(ticket);
        logger.info("{}补办年票成功");
    }

    /**
     * 获得续费后的日期
     * @param endTime
     * @param day
     * @return
     */
    private Date getRenewAfterTime(Date endTime, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + day);
        return calendar.getTime();
    }

    /**
     *获得一年后的日期类型参数
     * @return 一年后的日期
     */
    private Date getOutTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 365);
        return calendar.getTime();
    }
}
