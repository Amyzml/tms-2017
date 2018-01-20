package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.Scenic;
import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.entity.ScenicExample;
import com.kaishengit.tms.service.ScenicService;
import com.kaishengit.tms.service.mapper.ScenicAccountMapper;
import com.kaishengit.tms.service.mapper.ScenicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author NativeBoy
 */
@Service
public class ScenicServiceImpl implements ScenicService {


    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private ScenicAccountMapper scenicAccountMapper;

    private Logger logger = LoggerFactory.getLogger(ScenicServiceImpl.class);

    /**
     * 查询所有景区信息列表
     * @return
     */
    @Override
    public List<Scenic> findAll() {
        return scenicMapper.selectByExample(new ScenicExample());
    }

    /**
     * 根据id查找scenic
     * @param id
     * @return
     */
    @Override
    public Scenic findById(Integer id) {
        return scenicMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存景区信息
     * @param scenic
     */
    @Override
    public void saveScenic(Scenic scenic) {
        scenicMapper.insertSelective(scenic);
        logger.info("{}保存景区信息成功" + scenic.getId());
    }

    /**
     * 更新景区信息
     * @param scenic
     */
    @Override
    public void updateScenic(Scenic scenic) {
        scenicMapper.updateByPrimaryKey(scenic);
    }

    /**
     * 保存创建景区账户
     *
     * @param scenicAccount 景区账户
     * @param scenicId      景区id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveScenicAccount(ScenicAccount scenicAccount, Integer scenicId) {
        scenicAccount.setCreateTime(new Date());
        scenicAccount.setScenicId(scenicId);
        scenicAccount.setUpdateTime(new Date());
        scenicAccount.setScenicState("正常");
        scenicAccountMapper.insertSelective(scenicAccount);

        //绑定站点与站点账号
        Scenic scenic = scenicMapper.selectByPrimaryKey(scenicId);
        scenic.setScenicAccountId(scenicAccount.getId());
        scenicMapper.updateByPrimaryKeySelective(scenic);

        logger.info("{}创建景区账户成功>>>" + scenicAccount.getScenicAccount());
    }
}
