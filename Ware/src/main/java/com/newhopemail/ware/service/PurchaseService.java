package com.newhopemail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.ware.entity.PurchaseEntity;
import com.newhopemail.ware.vo.MargeVO;

import java.util.Map;

/**
 * 采购信息
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-05-09 02:28:30
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    void margeList(MargeVO margeVO);
}

