package com.newhopemail.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class MargeVO {
    private Long purchaseId;
    List<Long> items;
}
