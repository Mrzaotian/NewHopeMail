package com.newhopemail.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 02:50:56
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

