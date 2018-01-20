package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.entity.StoreAccount;

import java.util.List;

/**
 *
 * @author NativeBoy
 */
public interface AccountService {


    /**
     * 根据用户唯一的用户名进行查询账户
     * @param username
     * @return
     */
    Account findByUsername(String username);

    /**
     * 登录日志
     * @param ip 登录的IP地址
     * @param accoutId 登录用户的主键id
     */
    void saveWriteLoginLog(String ip, Integer accoutId);

    /**
     * 售票站点登录日志
     * @param ip 登录的IP地址
     * @param accoutId 登录用户的主键id
     */
    void saveWriteLoginLogWithStoreAccount(String ip, Integer accoutId);
    /**
     * 景区登录日志
     * @param ip 登录的IP地址
     * @param accoutId 登录用户的主键id
     */
    void saveWriteLoginLogWithScenicAccount(String ip, Integer accoutId);

    /**
     * 根据登录用户的id获取对应的角色列表
     * @param accountId
     * @return
     */
    List<Role> findRoleListByAccountId(Integer accountId);

    /**
     * 保存新增账户
     * @param account
     */
    void saveNewAccount(Account account);

    List<Account> fidnAll();


    Account findAccountById(Integer id);

    /**
     *
     * @param username
     * @return
     */
    StoreAccount findStoreAccountByUsername(String username);

    ScenicAccount findScenicAccountByUsername(String username);
}
