package com.newhopemail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.entity.PmsSpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-25 07:52:33
 */
public interface PmsSpuInfoService extends IService<PmsSpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

