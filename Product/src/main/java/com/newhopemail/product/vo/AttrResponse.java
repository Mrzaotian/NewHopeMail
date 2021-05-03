package com.newhopemail.product.vo;

import lombok.Data;

@Data
public class AttrResponse extends AttrVo{

    private String catelogName;

    private String groupName;

    private Long[] catelogPath;

}
