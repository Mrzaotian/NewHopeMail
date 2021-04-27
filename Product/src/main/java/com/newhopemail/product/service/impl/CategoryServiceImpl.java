package com.newhopemail.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.Query;

import com.newhopemail.product.dao.CategoryDao;
import com.newhopemail.product.entity.CategoryEntity;
import com.newhopemail.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
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
//        TODO
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
}