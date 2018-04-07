package com.ifast.demo;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 测试专用
 * </pre>
 * 
 * <small> 2018年1月16日 | Aron</small>
 */
@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/redis")
    public void redis() {
        Set<String> keys = stringRedisTemplate.keys("*");
        System.out.println(keys);
        stringRedisTemplate.opsForValue().set("Aron", "hello Aron.");
        String aron = stringRedisTemplate.opsForValue().get("Aron");
        System.out.println(aron);

        Long expire = stringRedisTemplate.getExpire("Aron");
        Long expire2 = stringRedisTemplate.getExpire("Aron", TimeUnit.MICROSECONDS);
        Long expire3 = stringRedisTemplate.getExpire("Aron", TimeUnit.SECONDS);
        System.out.println(expire);
        System.out.println(expire2);
        System.out.println(expire3);

    }

}
