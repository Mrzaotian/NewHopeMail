package com.newhopemail.product;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.controller.CategoryController;
import com.newhopemail.product.service.AttrGroupService;
import com.newhopemail.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class MyTest {
    @Resource
     AttrGroupService attrGroupService;
    @Test
    public void Test(){
        Map<String, Object> map=new HashMap<>();
        map.put("page","1");
        map.put("limit","10");
        PageUtils relation = attrGroupService.getNoRelation(7L, map);
        log.info("当前分页情况{}",relation.getList());
    }
}
