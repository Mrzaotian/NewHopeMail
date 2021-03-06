package com.newhopemail.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.newhopemail.member.feign.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newhopemail.member.entity.MemberEntity;
import com.newhopemail.member.service.MemberService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.R;



/**
 * 会员
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 03:10:33
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponService couponService;
    @RequestMapping("/coupons")
    public  R coupons(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("苹果");
        R member = couponService.member();
        return R.ok().put("member", memberEntity).put("coupon",member.get("coupon"));
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
