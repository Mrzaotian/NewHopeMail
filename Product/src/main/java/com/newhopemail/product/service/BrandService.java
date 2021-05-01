package com.newhopemail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

