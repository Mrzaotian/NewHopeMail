package com.newhopemail.ware.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.ware.dao.PurchaseDetailDao;
import com.newhopemail.ware.entity.PurchaseDetailEntity;
import com.newhopemail.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)){
            wrapper.and(w-> w.eq("purchase_id",key)
                    .or().like("sku_id",key));
        }
        String wareId = (String) params.get("wareId");
        if (StringUtils.isNotBlank(wareId)&&!"0".equalsIgnoreCase(wareId)){
            wrapper.eq("ware_id",wareId);
        }
        String status = (String) params.get("status");
        if (StringUtils.isNotBlank(status)&&!"0".equalsIgnoreCase(status)){
            wrapper.eq("status",status);
        }
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}