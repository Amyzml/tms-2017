package com.kaishengit.tms.service.mapper;

import com.kaishengit.tms.entity.ScenicLoginLog;
import com.kaishengit.tms.entity.ScenicLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScenicLoginLogMapper {
    long countByExample(ScenicLoginLogExample example);

    int deleteByExample(ScenicLoginLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScenicLoginLog record);

    int insertSelective(ScenicLoginLog record);

    List<ScenicLoginLog> selectByExample(ScenicLoginLogExample example);

    ScenicLoginLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScenicLoginLog record, @Param("example") ScenicLoginLogExample example);

    int updateByExample(@Param("record") ScenicLoginLog record, @Param("example") ScenicLoginLogExample example);

    int updateByPrimaryKeySelective(ScenicLoginLog record);

    int updateByPrimaryKey(ScenicLoginLog record);
}