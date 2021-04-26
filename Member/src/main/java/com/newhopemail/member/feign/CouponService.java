package com.newhopemail.member.feign;

import com.newhopemail.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "coupon")
public interface CouponService {
    @RequestMapping("/coupon/coupon/member/list")
    public R member();
}
