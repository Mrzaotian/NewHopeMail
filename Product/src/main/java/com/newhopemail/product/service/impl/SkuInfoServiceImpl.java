package com.newhopemail.product.service.impl;

import com.newhopemail.product.entity.SpuInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.SkuInfoDao;
import com.newhopemail.product.entity.SkuInfoEntity;
import com.newhopemail.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)){
            wrapper.and(w-> w.eq("sku_id",key).or().like("sku_name",key));
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.isNotBlank(catelogId)&&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotBlank(brandId)&&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }
        String min = (String) params.get("min");
        if (StringUtils.isNotBlank(min)){
            wrapper.ge("price",min);
        }
        String max = (String) params.get("max");
        if (StringUtils.isNotBlank(max)&&new BigDecimal(max).compareTo(BigDecimal.ZERO)>0){
                    wrapper.le("price",max);
        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}