package com.newhopemail.product;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.product.service.AttrGroupService;
import com.newhopemail.product.service.BrandService;
import com.newhopemail.product.vo.AttrGroupWithAttrVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class MyTest {
    @Resource
    AttrGroupService attrGroupService;
    @Test
    public void Test(){
        List<AttrGroupWithAttrVo> attrGroupWithAttr = attrGroupService.getAttrGroupWithAttr(225L);
        log.info("当前获取情况{}",attrGroupWithAttr.toArray());
    }
}
