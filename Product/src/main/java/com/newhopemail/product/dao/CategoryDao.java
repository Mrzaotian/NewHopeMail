package com.newhopemail.product.dao;

import com.newhopemail.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
