package com.newhopemail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.newhopemail.common.constant.ProductConstant;
import com.newhopemail.product.dao.AttrAttrgroupRelationDao;
import com.newhopemail.product.dao.AttrGroupDao;
import com.newhopemail.product.dao.CategoryDao;
import com.newhopemail.product.entity.AttrAttrgroupRelationEntity;
import com.newhopemail.product.entity.AttrGroupEntity;
import com.newhopemail.product.entity.CategoryEntity;
import com.newhopemail.product.service.CategoryService;
import com.newhopemail.product.vo.AttrGroupRelationVo;
import com.newhopemail.product.vo.AttrResponse;
import com.newhopemail.product.vo.AttrVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.AttrDao;
import com.newhopemail.product.entity.AttrEntity;
import com.newhopemail.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Resource
    AttrAttrgroupRelationDao relationDao;

    @Resource
    AttrGroupDao attrGroupDao;

    @Resource
    CategoryDao categoryDao;

    @Resource
    CategoryService categoryService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryBaseById(Map<String, Object> params, Long catelogId, String type) {
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attr_type", "base".equals(type) ? 1 : 0);
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId);
        }
        if (StringUtils.isNotBlank(key))
            queryWrapper.and(obj -> obj.eq("attr_id", key).or().like("attr_name", key));
        IPage<AttrEntity> iPage = new Query<AttrEntity>().getPage(params);
        iPage = this.page(iPage, queryWrapper);
        List<AttrResponse> list = iPage.getRecords().stream().map(attr -> {
            AttrResponse attrResponse = new AttrResponse();
            BeanUtils.copyProperties(attr, attrResponse);
            QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId());
            AttrAttrgroupRelationEntity attrId = relationDao.selectOne(wrapper);
            if (attrId != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrResponse.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity catelog = categoryDao.selectById(attr.getCatelogId());
            if (catelog != null) {
                attrResponse.setCatelogName(catelog.getName());
            }
            return attrResponse;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(iPage);
        pageUtils.setList(list);
        return pageUtils;
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        if (attr.getAttrType() == ProductConstant.AttrCode.ATTR_BASE_TYPE.getCode()) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public AttrResponse getAttrInfo(Long attrId) {
        AttrResponse attrResponse = new AttrResponse();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrResponse);
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId);
        AttrAttrgroupRelationEntity relation = relationDao.selectOne(wrapper);
        if (relation != null) {
            Long groupId = relation.getAttrGroupId();
            attrResponse.setAttrGroupId(groupId);
            AttrGroupEntity group = attrGroupDao.selectById(groupId);
            if (group != null) {
                attrResponse.setGroupName(group.getAttrGroupName());
            }
        }
        Long catelogId = attrEntity.getCatelogId();
        Long[] path = categoryService.getPath(catelogId);
        attrResponse.setCatelogPath(path);
        CategoryEntity categoryEntity = categoryService.getById(catelogId);
        if (categoryEntity != null) {
            attrResponse.setCatelogName(categoryEntity.getName());
        }
        return attrResponse;
    }

    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);
        if (attr.getAttrType() == ProductConstant.AttrCode.ATTR_BASE_TYPE.getCode()) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            int count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (count > 0) {
                relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {
                relationDao.insert(relationEntity);
            }
        }
    }

    @Override
    public List<AttrEntity> getRelation(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntity = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> list = relationEntity.stream()
                .map(item -> item != null ? item.getAttrId() : null)
                .collect(Collectors.toList());
        return this.listByIds(list);
    }

    @Override
    public void removeRelation(AttrGroupRelationVo[] relationVos) {
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(relationVos)
                .stream().map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        relationDao.deleteRelation(entities);
    }


}