package com.kaishengit.tms.service.mapper;

import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TicketMapper {
    long countByExample(TicketExample example);

    int deleteByExample(TicketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    List<Ticket> selectByExample(TicketExample example);

    Ticket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByExample(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    String findMaxTicketId();

    Integer findMaxId();

    /**
     * 获取有效年票库存
     * @return
     */
    Long findStorageCanUse(String state);

    Integer findMinId();

    /**
     * 根据卡号查询年票对象
     * @param ticketNum
     * @return
     */
    List<Ticket> findTicketByTicketNum(String ticketNum);

    /**
     * 根据站点id获取年票列表
     * @param storeId
     * @return
     */
    List<Ticket> findTicketListByStoreId(Integer storeId);

    /**
     * 根据顾客id查询对应的年票,一一对应
     * @param id
     * @return
     */
    Ticket findTicketByCustomerId(Integer id);
    /**
     * 根据站点id查询本站已售年票列表
     * @param id 站点id
     * @return
     */
    List<Ticket> findAllTicketLocalSall(Integer id);



}