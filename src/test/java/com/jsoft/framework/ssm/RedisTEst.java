package com.jsoft.framework.ssm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

public class RedisTEst extends BaseJunit4Test {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("name","psy"  );
        String naem = (String) redisTemplate.opsForValue().get( "name" );
        log.info( "name:"+naem );
    }
}
