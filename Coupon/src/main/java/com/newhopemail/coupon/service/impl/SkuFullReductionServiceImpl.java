package com.newhopemail.coupon.service.impl;

import com.newhopemail.common.to.MemberPrice;
import com.newhopemail.common.to.SkuReductionTO;
import com.newhopemail.coupon.entity.MemberPriceEntity;
import com.newhopemail.coupon.entity.SkuLadderEntity;
import com.newhopemail.coupon.service.MemberPriceService;
import com.newhopemail.coupon.service.SkuLadderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.coupon.dao.SkuFullReductionDao;
import com.newhopemail.coupon.entity.SkuFullReductionEntity;
import com.newhopemail.coupon.service.SkuFullReductionService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {
    @Resource
    SkuLadderService skuLadderService;
    @Resource
    MemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void saveInfo(SkuReductionTO skuReduction) {
        Long skuId = skuReduction.getSkuId();
        SkuLadderEntity skuLadderEntity=new SkuLadderEntity();
        skuLadderEntity.setAddOther(skuReduction.getCountStatus());
        skuLadderEntity.setDiscount(skuReduction.getDiscount());
        skuLadderEntity.setFullCount(skuReduction.getFullCount());
        skuLadderEntity.setSkuId(skuId);
        if (skuLadderEntity.getFullCount()>0){
            skuLadderService.save(skuLadderEntity);
        }
        SkuFullReductionEntity skuFullReductionEntity=new SkuFullReductionEntity();
        skuFullReductionEntity.setSkuId(skuId);
        skuFullReductionEntity.setReducePrice(skuReduction.getReducePrice());
        skuFullReductionEntity.setFullPrice(skuReduction.getFullPrice());
        skuFullReductionEntity.setAddOther(skuReduction.getPriceStatus());
        if (skuFullReductionEntity.getFullPrice().compareTo(BigDecimal.ZERO)>0){
            this.save(skuFullReductionEntity);
        }
        List<MemberPrice> memberPrice = skuReduction.getMemberPrice();
        List<MemberPriceEntity>   collect= memberPrice.stream().map(member -> {
                MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                memberPriceEntity.setMemberLevelId(member.getId());
                memberPriceEntity.setMemberLevelName(member.getName());
                memberPriceEntity.setMemberPrice(member.getPrice());
                memberPriceEntity.setAddOther(1);
                memberPriceEntity.setSkuId(skuId);
                return memberPriceEntity;
            })
                .filter(item-> item.getMemberPrice().compareTo(BigDecimal.ZERO)>0)
                .collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}