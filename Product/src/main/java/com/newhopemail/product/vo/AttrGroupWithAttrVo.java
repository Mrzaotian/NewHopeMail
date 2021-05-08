package com.newhopemail.product.vo;

import com.newhopemail.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;
@Data
public class AttrGroupWithAttrVo {
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    /**
     *
     *子级菜单
     */
    private List<AttrEntity> attrs;

}
