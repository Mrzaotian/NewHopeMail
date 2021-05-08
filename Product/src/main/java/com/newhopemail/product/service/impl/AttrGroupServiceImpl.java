package com.newhopemail.product.service.impl;
import com.newhopemail.common.constant.ProductConstant;
import com.newhopemail.product.entity.AttrAttrgroupRelationEntity;
import com.newhopemail.product.entity.AttrEntity;
import com.newhopemail.product.service.AttrAttrgroupRelationService;
import com.newhopemail.product.service.AttrService;
import com.newhopemail.product.service.CategoryService;
import com.newhopemail.product.vo.AttrGroupRelationVo;
import com.newhopemail.product.vo.AttrGroupWithAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    AttrAttrgroupRelationService relationService;

    @Resource
    AttrService attrService;
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

    @Override
    public PageUtils getNoRelation(Long attrgroupId, Map<String, Object> map) {
        AttrGroupEntity attrGroupEntity = this.getById(attrgroupId);
        Long  catelogId= attrGroupEntity.getCatelogId();
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id", catelogId);
        List<AttrGroupEntity> list = this.list(wrapper);
        List<Long>   collect = list.stream()
                .map(AttrGroupEntity::getAttrGroupId)
                .collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> entities = relationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id",collect));
        List<Long> attrId = entities.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());
        QueryWrapper<AttrEntity> notInWrapper = new QueryWrapper<>();
        notInWrapper.eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrCode.ATTR_BASE_TYPE.getCode());
        if (attrId.size()>0) {
            notInWrapper.notIn("attr_id", attrId);
        }
        String key = (String) map.get("key");
        if (key!=null){
            notInWrapper.and((item) -> item.eq("attr_id", key)
                    .or()
                    .like("attr_name", key));
        }
        IPage<AttrEntity> page = new Query<AttrEntity>().getPage(map);
        page = attrService.page(page, notInWrapper);
        return new PageUtils(page);
    }

    @Override
    public void addAttrRelation(List<AttrGroupRelationVo> relationVo) {
        List<AttrAttrgroupRelationEntity> collect = relationVo.stream().map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationService.saveBatch(collect);
    }

    @Override
    public List<AttrGroupWithAttrVo> getAttrGroupWithAttr(Long catelogId) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
        List<AttrGroupEntity> attrGroupEntities = this.list(wrapper);
        List<AttrGroupWithAttrVo> collect = attrGroupEntities.stream().map(item -> {
            AttrGroupWithAttrVo groupWithAttrVo = new AttrGroupWithAttrVo();
            BeanUtils.copyProperties(item, groupWithAttrVo);
            List<AttrEntity> attr = attrService.getRelation(item.getAttrGroupId());
            groupWithAttrVo.setAttrs(attr);
            return groupWithAttrVo;
        }).collect(Collectors.toList());
        return collect;
    }


    public PageUtils wrapperKey(String key,QueryWrapper<AttrGroupEntity> queryWrapper,Map<String, Object> params){
        if (StringUtils.isNotBlank(key)){
            queryWrapper.and((obj)-> obj.eq("attr_group_id",key).or().like("attr_group_name",key));
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }
}