package com.kaishengit.tms.service.mapper;

import com.kaishengit.tms.entity.StoreLoginLog;
import com.kaishengit.tms.entity.StoreLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoreLoginLogMapper {
    long countByExample(StoreLoginLogExample example);

    int deleteByExample(StoreLoginLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoreLoginLog record);

    int insertSelective(StoreLoginLog record);

    List<StoreLoginLog> selectByExample(StoreLoginLogExample example);

    StoreLoginLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoreLoginLog record, @Param("example") StoreLoginLogExample example);

    int updateByExample(@Param("record") StoreLoginLog record, @Param("example") StoreLoginLogExample example);

    int updateByPrimaryKeySelective(StoreLoginLog record);

    int updateByPrimaryKey(StoreLoginLog record);
}