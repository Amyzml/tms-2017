package com.kaishengit.tms.service.mapper;

import com.kaishengit.tms.entity.AccountRoleExample;
import com.kaishengit.tms.entity.AccountRoleKey;
import java.util.List;

import com.kaishengit.tms.entity.Role;
import org.apache.ibatis.annotations.Param;

public interface AccountRoleMapper {
    long countByExample(AccountRoleExample example);

    int deleteByExample(AccountRoleExample example);

    int deleteByPrimaryKey(AccountRoleKey key);

    int insert(AccountRoleKey record);

    int insertSelective(AccountRoleKey record);

    List<AccountRoleKey> selectByExample(AccountRoleExample example);

    int updateByExampleSelective(@Param("record") AccountRoleKey record, @Param("example") AccountRoleExample example);

    int updateByExample(@Param("record") AccountRoleKey record, @Param("example") AccountRoleExample example);

    /**
     * 根据登录用户的id获取对应的角色列表
     * @param accountId
     * @return
     */
    List<Role> findRoleListByAccountId(@Param("accountId") Integer accountId);
}