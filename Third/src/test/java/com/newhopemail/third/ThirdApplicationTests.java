package com.newhopemail.third;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class ThirdApplicationTests {
    @Resource
    OSSClient ossClient;
    @Test
    void contextLoads() throws FileNotFoundException {
        InputStream is=new FileInputStream("D:\\Typora\\resources\\app\\Docs\\img\\pandoc-win.PNG");
        ossClient.putObject("newhopemail","third.png",is);
        ossClient.shutdown();
    }

}
