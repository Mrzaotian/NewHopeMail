package com.newhopemail.member.dao;

import com.newhopemail.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 03:10:33
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
