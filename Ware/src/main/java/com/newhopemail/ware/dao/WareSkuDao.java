package com.newhopemail.ware.dao;

import com.newhopemail.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * εεεΊε­
 * 
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 03:38:08
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addPurchase(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
