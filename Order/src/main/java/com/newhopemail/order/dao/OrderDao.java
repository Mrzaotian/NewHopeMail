package com.newhopemail.order.dao;

import com.newhopemail.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 03:28:27
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
