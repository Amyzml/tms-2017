package com.kaishengit.tms.service.impl;


import com.kaishengit.tms.entity.*;

import com.kaishengit.tms.service.AccountService;
import com.kaishengit.tms.service.mapper.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author NativeBoy
 */

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;

    @Autowired
    private StoreLoginLogMapper storeLoginLogMapper;
    @Autowired
    private ScenicLoginLogMapper scenicLoginLogMapper;

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Autowired
    private StoreAccountMapper storeAccountMapper;

    @Autowired
    private ScenicAccountMapper scenicAccountMapper;

    private Logger logger = Logger.getLogger(AccountServiceImpl.class);
    /**
     * 根据用户唯一的用户名进行查询账户
     *
     * @param username
     * @return
     */
    @Override
    public Account findByUsername(String username) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(username);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(accountList != null && !accountList.isEmpty()){
            return accountList.get(0);
        }
        return null;
    }

    /**
     * 登录日志
     * @param ip       登录的IP地址
     * @param accoutId 登录用户的主键id
     */
    @Override
    public void saveWriteLoginLog(String ip, Integer accoutId) {
        AccountLoginLog accountLoginLog = new AccountLoginLog();
        accountLoginLog.setAccountId(accoutId);
        accountLoginLog.setLoginIp(ip);
        accountLoginLog.setLoginTime(new Date());
        accountLoginLogMapper.insertSelective(accountLoginLog);
        logger.info("{}保存一条登录日志" + accoutId);
    }

    /**
     * 登录日志
     * @param ip       登录的IP地址
     * @param accoutId 登录用户的主键id
     */
    @Override
    public void saveWriteLoginLogWithStoreAccount(String ip, Integer accoutId) {
        StoreLoginLog storeLoginLog = new StoreLoginLog();
        storeLoginLog.setLoginIp(ip);
        storeLoginLog.setLoginTime(new Date());
        storeLoginLog.setStoreAccountId(accoutId);
        storeLoginLogMapper.insertSelective(storeLoginLog);
        logger.info("{}保存一条登录日志" + accoutId + ">>>>IP:" + ip);
    }

    /**
     * 登录日志
     * @param ip       登录的IP地址
     * @param accoutId 登录用户的主键id
     */
    @Override
    public void saveWriteLoginLogWithScenicAccount(String ip, Integer accoutId) {
        ScenicLoginLog scenicLoginLog = new ScenicLoginLog();
        scenicLoginLog.setLoginIp(ip);
        scenicLoginLog.setLoginTime(new Date());
        scenicLoginLog.setScenicAccountId(accoutId);
        scenicLoginLogMapper.insertSelective(scenicLoginLog);
        logger.info("{}保存一条登录日志" + accoutId + ">>>>IP:" + ip);
    }

    /**
     * 根据登录用户的id获取对应的角色列表
     * @param accountId
     * @return
     */
    @Override
    public List<Role> findRoleListByAccountId(Integer accountId) {
        logger.info("查询账户对应的角色列表");
        return accountRoleMapper.findRoleListByAccountId(accountId);

    }

    /**
     * 保存新增账户
     * @param account
     */
    @Override
    public void saveNewAccount(Account account) {
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        account.setAccountState("正常");
        accountMapper.insertSelective(account);
        logger.info("{>>>>>>>} 保存账户成功" + account.getId());
    }

    @Override
    public List<Account> fidnAll() {
        return accountMapper.selectByExample(new AccountExample());
    }

    @Override
    public Account findAccountById(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    /**
     * @param username
     * @return
     */
    @Override
    public StoreAccount findStoreAccountByUsername(String username) {
        StoreAccountExample storeAccountExample = new StoreAccountExample();
        storeAccountExample.createCriteria().andStoreAccountEqualTo(username);
        List<StoreAccount> storeAccountList = storeAccountMapper.selectByExample(storeAccountExample);
        if(!storeAccountList.isEmpty() && storeAccountList != null){
            return storeAccountList.get(0);
        }
        return null;
    }

    @Override
    public ScenicAccount findScenicAccountByUsername(String username) {
        ScenicAccountExample scenicAccountExample = new ScenicAccountExample();
        scenicAccountExample.createCriteria().andScenicAccountEqualTo(username);
        List<ScenicAccount> scenicAccountList = scenicAccountMapper.selectByExample(scenicAccountExample);
        if(scenicAccountList != null && !scenicAccountList.isEmpty()){
            return scenicAccountList.get(0);
        }
        return null;
    }


}
