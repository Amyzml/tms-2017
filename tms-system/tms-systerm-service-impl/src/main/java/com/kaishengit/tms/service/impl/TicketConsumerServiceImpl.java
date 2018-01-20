package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.TicketConsumer;
import com.kaishengit.tms.service.TicketConsumerService;
import com.kaishengit.tms.service.mapper.TicketConsumerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 景区验票业务处理
 * @author NativeBoy
 */
@Service
public class TicketConsumerServiceImpl implements TicketConsumerService {


    @Autowired
    private TicketConsumerMapper ticketConsumerMapper;

    private Logger logger = LoggerFactory.getLogger(TicketConsumerServiceImpl.class);
    /**
     * 验票通过,新增一条消费记录
     *
     * @param ticketId        年票id
     * @param scenicAccountId 景区账号id
     */
    @Override
    public void saveNewConsumerOrder(Integer ticketId, Integer scenicAccountId) {


        String id = System.currentTimeMillis() + ticketId.toString() + scenicAccountId.toString()  ;
        TicketConsumer ticketConsumer = new TicketConsumer();
        ticketConsumer.setCreateTime(new Date());
        ticketConsumer.setScenicAccountId(scenicAccountId);
        ticketConsumer.setTicketId(ticketId);
        ticketConsumer.setId(id);
        ticketConsumer.setSettlement((byte) 188);

        ticketConsumerMapper.insertSelective(ticketConsumer);
        logger.info("{}景区刷卡验票通过,新增一条消费记录");
    }
}
