package com.newhopemail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-05-09 02:28:30
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

