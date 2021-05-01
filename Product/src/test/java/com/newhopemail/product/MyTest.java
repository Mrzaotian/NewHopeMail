package com.newhopemail.product;
import com.newhopemail.product.controller.CategoryController;
import com.newhopemail.product.service.AttrGroupService;
import com.newhopemail.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
@Slf4j
@SpringBootTest
public class MyTest {
    @Resource
    CategoryController categoryController;
    @Test
    public void Test(){
    }
}
