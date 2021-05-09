package com.newhopemail.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseVo {
    @NotNull
    private Long id;
    @NotNull
    private List<PurchaseItemVo> items;
}
