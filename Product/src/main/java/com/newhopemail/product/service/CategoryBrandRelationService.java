package com.newhopemail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateDetail(Long brandId, String name);

    void updateCategory(Long catId, String name);
}

