package com.newhopemail.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.PmsSkuSaleAttrValueDao;
import com.newhopemail.product.entity.PmsSkuSaleAttrValueEntity;
import com.newhopemail.product.service.PmsSkuSaleAttrValueService;


@Service("pmsSkuSaleAttrValueService")
public class PmsSkuSaleAttrValueServiceImpl extends ServiceImpl<PmsSkuSaleAttrValueDao, PmsSkuSaleAttrValueEntity> implements PmsSkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsSkuSaleAttrValueEntity> page = this.page(
                new Query<PmsSkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<PmsSkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

}