package com.newhopemail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.newhopemail.product.entity.AttrAttrgroupRelationEntity;
import com.newhopemail.product.entity.AttrEntity;
import com.newhopemail.product.service.AttrAttrgroupRelationService;
import com.newhopemail.product.service.AttrService;
import com.newhopemail.product.vo.AttrGroupRelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.newhopemail.product.entity.AttrGroupEntity;
import com.newhopemail.product.service.AttrGroupService;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.common.utils.R;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author zao
 * @email mr.zaotian@gmail.com
 * @date 2021-04-26 01:58:32
 */
@Slf4j
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Resource
    private AttrGroupService attrGroupService;
    @Resource
    private AttrService attrService;
    /**
     *
     *获取关联信息
     * @param attrgroupId
     * @return
     */

    @GetMapping("{attrgroupId}/attr/relation")
    public R getRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> list= attrService.getRelation(attrgroupId);
        return R.ok().put("data",list);
    }

    /**
     * 添加关联信息
     *
     */
    @PostMapping("attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> relationVo){
        attrGroupService.addAttrRelation(relationVo);

        return R.ok();
    }


    /**
     *
     * 获取没有关联的分组信息
     */
    @GetMapping("{attrgroupId}/noattr/relation")
    public R getNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                           @RequestParam Map<String,Object> map){
        PageUtils pageUtils=attrGroupService.getNoRelation(attrgroupId,map);
        log.info(pageUtils.toString());
        return R.ok().put("page",pageUtils);
    }

    /**
     * 删除关联信息
     *
     */
    @PostMapping("attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] relationVos){
        attrService.removeRelation(relationVos);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catelogId){
        PageUtils page=attrGroupService.queryPageById(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] path=attrGroupService.getPath(attrGroup.getAttrGroupId());
        attrGroup.setPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }
}
