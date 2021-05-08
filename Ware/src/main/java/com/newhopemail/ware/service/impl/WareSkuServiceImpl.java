package com.newhopemail.ware.service.impl;

import com.newhopemail.ware.entity.WareInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.ware.dao.WareSkuDao;
import com.newhopemail.ware.entity.WareSkuEntity;
import com.newhopemail.ware.service.WareSkuService;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String skuId = (String) params.get("skuId");
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(skuId)&&!"0".equalsIgnoreCase(skuId)){
            wrapper.eq("sku_id",skuId);
        }
        String wareId = (String) params.get("wareId");
        if (StringUtils.isNotBlank(wareId)&&!"0".equalsIgnoreCase(wareId)){
            wrapper.eq("ware_id",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}