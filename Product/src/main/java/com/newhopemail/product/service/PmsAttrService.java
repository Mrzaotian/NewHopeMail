package com.newhopemail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.entity.PmsAttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-25 07:52:33
 */
public interface PmsAttrService extends IService<PmsAttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

