package com.zhouchuanxiang.inputlab.redis;

import com.zhouchuanxiang.inputlab.redis.tool.RedisCacheTool;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@SpringBootTest(classes = InputLabRedisApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    RedisCacheTool redisCacheTool;
    @Test
    public void testConnection() {
        try {
            redisTemplate.opsForValue().set("test-key", "hello-redis");
            String value = redisTemplate.opsForValue().get("test-key");
            System.out.println("Redis连接成功，测试值: " + value);
        } catch (Exception e) {
            System.err.println("Redis连接失败: " + e.getMessage());
        }
    }

    @Test
    public void testRedisCacheTool () {

        redisCacheTool.setString("test1","testvalue",120, TimeUnit.SECONDS);

        System.out.println(redisCacheTool.getString("test1"));
    }
}