package com.newhopemail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.entity.AttrEntity;
import com.newhopemail.product.vo.AttrResponse;
import com.newhopemail.product.vo.AttrVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryBaseById(Map<String, Object> params, Long catelogId, String type);

    void saveAttr(AttrVo attr);

    AttrResponse getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);
}

