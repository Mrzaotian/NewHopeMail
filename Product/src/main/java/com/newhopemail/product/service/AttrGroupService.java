package com.newhopemail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.entity.AttrEntity;
import com.newhopemail.product.entity.AttrGroupEntity;
import com.newhopemail.product.vo.AttrGroupRelationVo;
import com.newhopemail.product.vo.AttrGroupWithAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {
    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageById(Map<String, Object> params, Long catelogId);

    Long[] getPath(Long attrGroupId);

    PageUtils getNoRelation(Long attrgroupId, Map<String, Object> map);

    void addAttrRelation(List<AttrGroupRelationVo> relationVo);

    List<AttrGroupWithAttrVo> getAttrGroupWithAttr(Long catelogId);
}

