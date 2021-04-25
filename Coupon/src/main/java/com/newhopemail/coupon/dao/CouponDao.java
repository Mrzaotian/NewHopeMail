package com.newhopemail.coupon.dao;

import com.newhopemail.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 02:50:56
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
