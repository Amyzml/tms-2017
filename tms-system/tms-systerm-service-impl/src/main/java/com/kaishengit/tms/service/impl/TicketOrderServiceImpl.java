package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.TicketOrder;
import com.kaishengit.tms.service.TicketOrderService;
import com.kaishengit.tms.service.mapper.TicketOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 年票订单业务处理
 * @author NativeBoy
 */
@Service
public class TicketOrderServiceImpl implements TicketOrderService {

    private Logger logger = LoggerFactory.getLogger(TicketOrderServiceImpl.class);

    @Autowired
    private TicketOrderMapper ticketOrderMapper;
    /**
     * 新增年票订单记录
     * 订单类型: 办理年票 默认价格:100元
     *订单号生成策略 storeAccountId +  ticketId  + customerId
     * @param customerId     顾客id
     * @param ticketId       年票id
     * @param storeAccountId 售票站点id
     */
    @Override
    public void saveNewTicketOrder(Integer customerId, Integer ticketId, Integer storeAccountId) {

        TicketOrder ticketOrder = new TicketOrder();
        String orderNum = storeAccountId.toString() + ticketId.toString() + customerId.toString();

        ticketOrder.setCreateTime(new Date());
        ticketOrder.setCustomerId(customerId);
        ticketOrder.setTicketId(ticketId);
        ticketOrder.setTicketOrderType("办理年票");
        ticketOrder.setTicketOrderPrice(new BigDecimal(100));
        ticketOrder.setTicketOrderNum(orderNum);
        ticketOrder.setStoreAccountId(storeAccountId);

        ticketOrderMapper.insertSelective(ticketOrder);
        logger.info("{}新增一条年票订单" + ticketOrder.getTicketOrderNum());
    }

    /**
     * 补办年票订单记录
     *补办年票:20元   订单类型:补办年票
     * @param ticketId   年票id
     * @param accountId  售票点账户id
     * @param customerId 客户id
     */
    @Override
    public void saveTicketOrderBecauseOldToNew(String ticketId, Integer accountId, Integer customerId) {
        TicketOrder ticketOrder = new TicketOrder();
        String orderNum = accountId.toString() + ticketId + customerId.toString();

        ticketOrder.setCreateTime(new Date());
        ticketOrder.setCustomerId(customerId);
        ticketOrder.setTicketId(Integer.valueOf(ticketId));
        ticketOrder.setTicketOrderType("补办年票");
        ticketOrder.setTicketOrderPrice(new BigDecimal(20));
        ticketOrder.setTicketOrderNum(orderNum);
        ticketOrder.setStoreAccountId(accountId);

        ticketOrderMapper.insertSelective(ticketOrder);
        logger.info("{}新增一条补办年票订单" + ticketOrder.getTicketOrderNum());
    }
}
