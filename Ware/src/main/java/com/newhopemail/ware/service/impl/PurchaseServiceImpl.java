package com.newhopemail.ware.service.impl;

import com.newhopemail.common.constant.WareConstant;
import com.newhopemail.ware.entity.PurchaseDetailEntity;
import com.newhopemail.ware.service.PurchaseDetailService;
import com.newhopemail.ware.vo.MargeVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.ware.dao.PurchaseDao;
import com.newhopemail.ware.entity.PurchaseEntity;
import com.newhopemail.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    @Resource
    PurchaseDetailService purchaseDetailService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status",0).or().eq("status",1);
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void margeList(MargeVO margeVO) {

        Long purchaseId = margeVO.getPurchaseId();
        if (purchaseId==null){
            PurchaseEntity purchaseEntity=new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseCode.CREATED.getCode());
            this.save(purchaseEntity);
        }
        List<Long> items = margeVO.getItems();
        List<PurchaseDetailEntity> collect = items.stream().map(i -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            detailEntity.setStatus(WareConstant.PurchaseDetailCode.CREATED.getCode());
            detailEntity.setId(i);
            detailEntity.setPurchaseId(purchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetailCode.ASSIGNED.getCode());
            return detailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);

    }

}