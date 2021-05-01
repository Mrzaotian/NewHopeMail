package com.newhopemail.product.service.impl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.newhopemail.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.AttrGroupDao;
import com.newhopemail.product.entity.AttrGroupEntity;
import com.newhopemail.product.service.AttrGroupService;

import javax.annotation.Resource;

@Slf4j
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Resource
    CategoryService categoryService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageById(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> queryWrapper=new QueryWrapper<>();
        if(catelogId==0)return wrapperKey(key,queryWrapper,params);
        queryWrapper.eq("catelog_id",catelogId);
        return wrapperKey(key,queryWrapper,params);
    }

    @Override
    public Long[] getPath(Long attrGroupId) {
        return categoryService.getPath(this.getById(attrGroupId).getCatelogId());
    }


    public PageUtils wrapperKey(String key,QueryWrapper<AttrGroupEntity> queryWrapper,Map<String, Object> params){
        if (StringUtils.isNotBlank(key)){
            queryWrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }
}