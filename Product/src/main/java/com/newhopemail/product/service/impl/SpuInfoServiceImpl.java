package com.newhopemail.product.service.impl;

import com.newhopemail.common.to.MemberPrice;
import com.newhopemail.common.to.SkuReductionTO;
import com.newhopemail.common.to.SpuBoundsTO;
import com.newhopemail.product.entity.*;
import com.newhopemail.product.feign.SpuCoupon;
import com.newhopemail.product.service.*;
import com.newhopemail.product.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Slf4j
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Resource
    SpuInfoDescService spuInfoDescService;
    @Resource
    SpuImagesService imagesService;
    @Resource
    ProductAttrValueService productAttrValueService;
    @Resource
    AttrService attrService;
    @Resource
    SkuInfoService skuInfoService;
    @Resource
    SkuImagesService skuImagesService;
    @Resource
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Resource
    SpuCoupon spuCoupon;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)){
            wrapper.and(w->{
                w.eq("id",key).or().like("spu_name",key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.isNotBlank(catelogId)&&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotBlank(brandId)&&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }
        String publishStatus = (String) params.get("status");
        if (StringUtils.isNotBlank(publishStatus)&&!"0".equalsIgnoreCase(publishStatus)){
            wrapper.eq("publish_status",publishStatus);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                    wrapper
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void saveInfo(SpuInfoVo spuInfo) {
        SpuInfoEntity spuInfoEntity=new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);
        List<String> decript = spuInfo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity=new SpuInfoDescEntity();
        Long id = spuInfoEntity.getId();
        spuInfoDescEntity.setSpuId(id);
        spuInfoDescEntity.setDecript(String.join(",",decript));
        spuInfoDescService.save(spuInfoDescEntity);
        List<String> images = spuInfo.getImages();
        imagesService.saveImages(id,images);
        List<BaseAttrs> baseAttrs = spuInfo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(item -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            Long attrId = item.getAttrId();
            productAttrValueEntity.setAttrId(attrId);
            productAttrValueEntity.setAttrValue(item.getAttrValues());
            productAttrValueEntity.setSpuId(id);
            productAttrValueEntity.setQuickShow(item.getShowDesc());
            AttrEntity attr= attrService.getById(attrId);
            productAttrValueEntity.setAttrName(attr.getAttrName());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);
        Long catalogId = spuInfo.getCatalogId();
        Long brandId = spuInfo.getBrandId();
        List<Skus> skus = spuInfo.getSkus();
        Bounds bounds = spuInfo.getBounds();
        SpuBoundsTO spuBoundsTO=new SpuBoundsTO();
        BeanUtils.copyProperties(bounds,spuBoundsTO);
        spuBoundsTO.setSpuId(id);
        spuCoupon.saveBounds(spuBoundsTO);
        if (!CollectionUtils.isEmpty(skus)){
            skus.forEach(item -> {
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setSpuId(id);
                skuInfoEntity.setCatalogId(catalogId);
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setBrandId(brandId);
                String defaultImage = "";
                List<Images> images1 = item.getImages();
                for (Images img : images1) {
                    if (img.getDefaultImg() == 1) {
                        defaultImage = img.getImgUrl();
                    }
                }
                skuInfoEntity.setSkuDefaultImg(defaultImage);
                skuInfoService.save(skuInfoEntity);
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> collect1 = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    return skuImagesEntity;
                }).filter(entity-> StringUtils.isNotBlank(entity.getImgUrl()))
                        .collect(Collectors.toList());
                skuImagesService.saveBatch(collect1);
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> collect2 = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity sse = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, sse);
                    sse.setSkuId(skuId);
                    return sse;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(collect2);
                SkuReductionTO reductionTO=new SkuReductionTO();
                BeanUtils.copyProperties(item,reductionTO);
                reductionTO.setSkuId(skuId);
                if (reductionTO.getFullCount()>0||reductionTO.getFullPrice().compareTo(BigDecimal.ZERO)>0){
                    spuCoupon.saveSpuReduction(reductionTO);
                }
            });
        }
    }
}