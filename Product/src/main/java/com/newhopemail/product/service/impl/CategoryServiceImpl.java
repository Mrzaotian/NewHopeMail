package com.newhopemail.product.service.impl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.newhopemail.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.CategoryDao;
import com.newhopemail.product.entity.CategoryEntity;
import com.newhopemail.product.service.CategoryService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Resource
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listCategory() {
        List<CategoryEntity> categoryEntities = this.list();
        List<CategoryEntity> collect = categoryEntities.stream()
                                        .filter(entity -> entity.getParentCid() == 0)
                                        .peek(menu-> menu.setChildren(listChildren(menu,categoryEntities)))
                                        .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                                        .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeBatch(List<Long> asList) {
        this.removeByIds(asList);
    }

    private  List<CategoryEntity> listChildren(CategoryEntity entity,List<CategoryEntity> entities){
        List<CategoryEntity> collect = entities.stream()
                .filter(category -> category.getParentCid().equals(entity.getCatId()))
                .peek(category -> category.setChildren(listChildren(category, entities)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Long[] getPath(Long categoryId) {
        List<Long> path=new ArrayList<>();
        getChildPath(categoryId,path);
        Collections.reverse(path);
        return path.toArray(new Long[path.size()]);
    }
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.isNotBlank(category.getName()))
            categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    public void getChildPath(Long categoryId, List<Long> path){
        if (categoryId==0)return;
        path.add(categoryId);
        CategoryEntity categoryEntity = this.getById(categoryId);
        getChildPath(categoryEntity.getParentCid(),path);
    }
}