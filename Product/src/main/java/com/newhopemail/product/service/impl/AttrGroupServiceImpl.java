package com.newhopemail.product.service.impl;
import com.newhopemail.product.service.CategoryService;
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
        if(catelogId==0){
            IPage<AttrGroupEntity> iPage=this.page(new Query<AttrGroupEntity>().getPage(params));
            return new PageUtils(iPage);
        }
        QueryWrapper<AttrGroupEntity> queryWrapper=new QueryWrapper<AttrGroupEntity>().eq("catelog_id",catelogId);
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)){
            queryWrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public Long[] getPath(Long attrGroupId) {
        return categoryService.getPath(this.getById(attrGroupId).getCatelogId());
    }
}