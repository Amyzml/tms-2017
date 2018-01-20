package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Scenic;
import com.kaishengit.tms.entity.ScenicAccount;

import java.util.List;

/**
 * 景区信息service
 * @author NativeBoy
 */
public interface ScenicService {

    /**
     * 查询所有景区信息列表
     * @return
     */
    List<Scenic> findAll();

    /**
     * 根据id查找scenic
     * @param id
     * @return
     */
    Scenic findById(Integer id);

    /**
     * 保存景区信息
     * @param scenic
     */
    void saveScenic(Scenic scenic);

    /**
     * 更新景区信息
     * @param scenic
     */
    void updateScenic(Scenic scenic);

    /**
     * 保存创建景区账户
     * @param scenicAccount 景区账户
     * @param scenicId 景区id
     */
    void saveScenicAccount(ScenicAccount scenicAccount, Integer scenicId);
}
