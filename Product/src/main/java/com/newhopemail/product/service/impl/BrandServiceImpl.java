package com.newhopemail.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.newhopemail.product.entity.CategoryEntity;
import com.newhopemail.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.BrandDao;
import com.newhopemail.product.entity.BrandEntity;
import com.newhopemail.product.service.BrandService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Resource
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key))
            wrapper.eq("brand_id",key).or().like("name",key);
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand);
        if (StringUtils.isNotBlank(brand.getName()))
            categoryBrandRelationService.updateDetail(brand.getBrandId(),brand.getName());
    }

}