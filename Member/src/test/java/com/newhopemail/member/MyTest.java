package com.newhopemail.member;
import com.newhopemail.common.utils.PageUtils;
import com.newhopemail.member.service.MemberLevelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class MyTest {
    @Resource
     MemberLevelService memberLevelService;
    @Test
    public void Test(){
        Map<String, Object> map=new HashMap<>();
        map.put("page","1");
        map.put("limit","10");
        PageUtils relation = memberLevelService.queryPage(map);
        log.info("当前分页情况{}",relation.getTotalPage());
    }
}
