package com.newhopemail.product;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newhopemail.product.entity.BrandEntity;
import com.newhopemail.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class ProductApplicationTests {
    @Autowired
    BrandService brandService;
    @Test
    public void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(5L);
//        brandEntity.setDescript("手机");
//        brandEntity.setName("苹果");
//        pmsBrandService.updateById(brandEntity);
        BrandEntity entity = brandService.getById(1);

        System.out.println(entity);
    }

}
