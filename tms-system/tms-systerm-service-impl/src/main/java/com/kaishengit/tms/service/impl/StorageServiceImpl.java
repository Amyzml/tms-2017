package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.service.StorageService;
import com.kaishengit.tms.service.TicketStoreService;
import com.kaishengit.tms.service.mapper.TicketMapper;
import com.kaishengit.tms.service.mapper.TicketStoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * @author NativeBoy
 */
@Service
public class StorageServiceImpl implements StorageService {


    private Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private TicketStoreMapper ticketStoreMapper;

    /**
     * 获得当前仓库年票的最大ID
     *
     * @return
     */
    @Override
    public String findMaxTicketNum() {
        Ticket ticket = ticketMapper.selectByPrimaryKey(ticketMapper.findMaxId());
        if(ticket == null){
            return "";
        }
        return ticket.getTicketNum();
    }

    /**
     * 获取当前仓库年票的最大ID
     *
     * @return
     */
    @Override
    public Integer findMaxId() {
        return ticketMapper.findMaxId();
    }

    /**
     * 年票入库
     *
     * @param integer  入库数量
     * @param beginNum 起始卡号
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void putToStorage(Integer integer, String beginNum) {

        if(beginNum.isEmpty() || beginNum == null){
            beginNum = "00000000";
        }
        Integer currNum = Integer.valueOf(beginNum);
        DecimalFormat format = new DecimalFormat("00000000");


        for(int i = 0; i < integer; i++){
            Ticket ticket = new Ticket();
            ticket.setTicketState("入库");
            ticket.setTicketInTime(new Date());
            ticket.setTicketNum(format.format(currNum));
            ticketMapper.insertSelective(ticket);
            currNum ++;
        }
        logger.info("{}入库" + integer + "张年票成功");
    }

    /**
     * 根据年票卡号查询年票信息
     *
     * @param ticketNum
     * @return
     */
    @Override
    public Ticket findTicketByTicketNum(String ticketNum) {
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);
        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);
        if(!ticketList.isEmpty() && ticketList != null){
            return ticketList.get(0);
        }
        throw new ServiceException("此年票不存在");
    }

    /**
     * 作废年票
     *
     * @param ticket 被作废对象
     */
    @Override
    public void setTicketCancellation(Ticket ticket) {
        ticket.setTicketState("作废");
        ticket.setUpdateTime(new Date());
        ticketMapper.updateByPrimaryKeySelective(ticket);
        logger.info("{}年票作废成功" + ticket.getTicketNum());
    }

    /**
     * 获取已经作废的年票卡号
     *
     * @return
     */
    @Override
    public List<Ticket> findListCancellation() {
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketStateEqualTo("作废");
        return ticketMapper.selectByExample(ticketExample);
    }

    /**
     * 获取当前有效的库存量
     *
     * @return
     */
    @Override
    public Long getTicketNumCanUse() {
        String state = "入库";
        return ticketMapper.findStorageCanUse(state);
    }

    /**
     * 获取库存可用的最小id
     * @return
     */
    @Override
    public Integer findMinId() {
        return ticketMapper.findMinId();
    }

    /**
     * 获取有效的可用年票的号码
     * @param id
     * @return
     */
    @Override
    public String findMinTicketNumById(Integer id) {
        Ticket ticket = ticketMapper.selectByPrimaryKey(id);
        return ticket.getTicketNum();
    }

    /**
     * 年票下发
     *
     * @param id 站点id
     * @param ticketNumber 下发数量
     * @param minCanUseCardNum 下发起始卡号
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveIssueTicket(Integer id, String ticketNumber, String minCanUseCardNum) {
        Integer currNum = Integer.valueOf(minCanUseCardNum);
        TicketStore ticketStore = ticketStoreMapper.selectByPrimaryKey(id);
        if(ticketStore == null){
            throw new ServiceException("该站点不存在");
        }
        DecimalFormat format = new DecimalFormat("00000000");

        for(int i = 0; i < Integer.valueOf(ticketNumber); i++){

            List<Ticket> ticketList = ticketMapper.findTicketByTicketNum(format.format(currNum));

            if(ticketList == null || ticketList.isEmpty()){

            }
            Ticket ticket = ticketList.get(0);
            if(!ticket.getTicketState().equals("入库")){
                continue;
            }
            ticket.setTicketState("下发");
            ticket.setStoreAccountId(id);
            ticket.setTicketOutTime(new Date());

            ticketMapper.updateByPrimaryKeySelective(ticket);
            currNum ++;
        }
        logger.info("{}年票下发到" + ticketStore.getStoreName() + ticketNumber + "成功");
    }

    /**
     * 根据站点id获取所属年票列表
     *
     * @param storeId
     * @return
     */
    @Override
    public List<Ticket> findTicketByStoreId(Integer storeId) {
        return ticketMapper.findTicketListByStoreId(storeId);
    }


}
