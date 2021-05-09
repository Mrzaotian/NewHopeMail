package com.newhopemail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newhopemail.product.entity.ProductAttrValueEntity;
import com.newhopemail.product.service.ProductAttrValueService;
import com.newhopemail.product.vo.AttrResponse;
import com.newhopemail.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.newhopemail.product.entity.AttrEntity;
import com.newhopemail.product.service.AttrService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.R;

import javax.annotation.Resource;


/**
 * 商品属性
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Resource
    ProductAttrValueService productAttrValueService;
    @RequestMapping("base/listforspu/{spuId}")
    public R baseList(@PathVariable Long spuId){
        List<ProductAttrValueEntity> list =productAttrValueService.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));;
        return R.ok().put("data",list);
    }
    @PostMapping("update/{spuId}")
    public R updateBySpuId(@PathVariable Long spuId,@RequestBody List<ProductAttrValueEntity> list){
        productAttrValueService.updateBySpuId(spuId,list);
        return R.ok();
    }
    @GetMapping("/{type}/list/{catelogId}")
    public R typeList(@RequestParam Map<String, Object> params,
                      @PathVariable("catelogId") Long catelogId,
                      @PathVariable("type")  String type){
        PageUtils page=attrService.queryBaseById(params,catelogId,type);
        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrResponse response = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", response);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
