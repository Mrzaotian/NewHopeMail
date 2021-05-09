package com.newhopemail.ware.service.impl;

import com.newhopemail.common.constant.WareConstant;
import com.newhopemail.common.utils.R;
import com.newhopemail.ware.dao.WareSkuDao;
import com.newhopemail.ware.entity.PurchaseDetailEntity;
import com.newhopemail.ware.entity.WareSkuEntity;
import com.newhopemail.ware.feign.ProductService;
import com.newhopemail.ware.service.PurchaseDetailService;
import com.newhopemail.ware.service.WareSkuService;
import com.newhopemail.ware.vo.MargeVO;
import com.newhopemail.ware.vo.PurchaseItemVo;
import com.newhopemail.ware.vo.PurchaseVo;
import org.springframework.stereotype.Service;

import java.util.*;
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
    @Resource
    WareSkuDao wareSkuDao;
    @Resource
    ProductService productService;
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
    public void mergeList(MargeVO margeVO) {

        Long purchaseId = margeVO.getPurchaseId();
        if (purchaseId==null){
            PurchaseEntity purchaseEntity=new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseCode.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId=purchaseEntity.getId();
        }
        List<Long> items = margeVO.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(i -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            detailEntity.setStatus(WareConstant.PurchaseDetailCode.CREATED.getCode());
            detailEntity.setId(i);
            detailEntity.setPurchaseId(finalPurchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetailCode.ASSIGNED.getCode());
            return detailEntity;
        }).collect(Collectors.toList());

        purchaseDetailService.updateBatchById(collect);

    }

    @Override
    public void received(List<Long> ids) {
        List<PurchaseEntity> purchaseEntities = this.listByIds(ids);
        List<PurchaseEntity> collect = purchaseEntities.stream().filter(item -> {
            Integer status = item.getStatus();
            return  status==WareConstant.PurchaseCode.CREATED.getCode()||status==WareConstant.PurchaseCode.ASSIGNED.getCode();
        }).peek(i-> i.setStatus(WareConstant.PurchaseCode.RECEIVED.getCode()))
                .collect(Collectors.toList());
        this.updateBatchById(collect);
        List<Long> collect1 = collect.stream().map(PurchaseEntity::getId)
                .collect(Collectors.toList());
        QueryWrapper<PurchaseDetailEntity> in =null;
        if (collect.size()>0){
             in=new QueryWrapper<PurchaseDetailEntity>().in("purchase_id", collect1);
        }
        List<PurchaseDetailEntity> detailEntities = purchaseDetailService.list(in);
        List<PurchaseDetailEntity> collect2 = detailEntities.stream().map(item -> {
            PurchaseDetailEntity entity = new PurchaseDetailEntity();
            entity.setId(item.getId());
            entity.setStatus(WareConstant.PurchaseDetailCode.BUYING.getCode());
            return entity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect2);
    }
    @Transactional
    @Override
    public void finish(PurchaseVo purchaseVo) {
        List<PurchaseItemVo> items = purchaseVo.getItems();
        boolean f=true;
        List<PurchaseDetailEntity> list=new ArrayList<>();
        List<Long> itemId=new ArrayList<>();
        for (PurchaseItemVo item : items) {
            PurchaseDetailEntity detailEntity=new PurchaseDetailEntity();
            if (item.getStatus()==WareConstant.PurchaseDetailCode.FAILED.getCode()){
                f=false;
            }else{
                itemId.add(item.getItemId());
            }
            detailEntity.setStatus(item.getStatus());
            detailEntity.setId(item.getItemId());
            list.add(detailEntity);
        }
        if (itemId.size()>0){
            List<PurchaseDetailEntity> list1 = purchaseDetailService.listByIds(itemId);
            if (list1.size()>0){
                for (PurchaseDetailEntity entity : list1) {
                    Long skuId = entity.getSkuId();
                    Long wareId = entity.getWareId();
                    Integer skuNum = entity.getSkuNum();
                    List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>()
                            .eq("sku_id", skuId)
                            .eq("ware_id", wareId));
                    if (wareSkuEntities==null||wareSkuEntities.size()==0){
                        WareSkuEntity wareSkuEntity=new WareSkuEntity();
                        wareSkuEntity.setSkuId(skuId);
                        wareSkuEntity.setWareId(wareId);
                        wareSkuEntity.setStock(skuNum);
                        try {
                            R info = productService.info(skuId);
                            if (info.getCode()==0){
                                Map<String,Object> skuName = (Map<String, Object>) info.get("skuInfo");
                                wareSkuEntity.setSkuName((String) skuName.get("skuName"));
                            }
                        }catch (Exception ignored){}

                        wareSkuEntity.setStockLocked(0);
                        wareSkuDao.insert(wareSkuEntity);
                    }else{
                        wareSkuDao.addPurchase(skuId,wareId,skuNum);
                    }
                }
            }
        }
        purchaseDetailService.updateBatchById(list);
        PurchaseEntity purchaseEntity=new PurchaseEntity();
        purchaseEntity.setId(purchaseVo.getId());
        purchaseEntity.setStatus(f?WareConstant.PurchaseCode.FINISHED.getCode() : WareConstant.PurchaseCode.EXCEPTION.getCode());
        this.updateById(purchaseEntity);
    }

}