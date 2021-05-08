package com.newhopemail.product.feign;
import com.newhopemail.common.to.SkuReductionTO;
import com.newhopemail.common.to.SpuBoundsTO;
import com.newhopemail.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "coupon")
public interface SpuCoupon {
    @RequestMapping("coupon/spubounds/save")
    R saveBounds(@RequestBody SpuBoundsTO spuBoundsTO);
    @RequestMapping("coupon/skufullreduction/saveInfo")
    R saveSpuReduction(@RequestBody SkuReductionTO reductionTO);
}
