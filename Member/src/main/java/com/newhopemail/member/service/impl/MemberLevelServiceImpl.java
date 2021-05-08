package com.newhopemail.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.member.dao.MemberLevelDao;
import com.newhopemail.member.entity.MemberLevelEntity;
import com.newhopemail.member.service.MemberLevelService;

@Slf4j
@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevelEntity> implements MemberLevelService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MemberLevelEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key))
            wrapper.eq("id",key).or().like("name",key);
        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}