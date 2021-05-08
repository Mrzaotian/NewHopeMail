/**
  * Copyright 2021 bejson.com 
  */
package com.newhopemail.product.vo;
import com.newhopemail.common.valid.AddGroup;
import com.newhopemail.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2021-05-07 18:28:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuInfoVo {
    @NotBlank(message = "名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String spuName;
    @NotBlank(message = "spu描述不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String spuDescription;

    @NotNull(message = "catalogId 不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Long catalogId;

    @NotNull(message = "brandId 不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Long brandId;

    @NotNull(message = "重量不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal weight;

    @NotNull(message = "发布状态不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Integer publishStatus;

    @Size(min = 1,message = "描述不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private List<String> decript;

    @Size(min = 1,message = "图片不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private List<String> images;

    @NotNull(message = "积分不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Bounds bounds;

    @Size(min = 1 ,message ="基本属性不能为空",groups = {AddGroup.class, UpdateGroup.class} )
    private List<BaseAttrs> baseAttrs;

    @Size(min = 1 ,message ="sku不能为空",groups = {AddGroup.class, UpdateGroup.class} )
    private List<Skus> skus;

}