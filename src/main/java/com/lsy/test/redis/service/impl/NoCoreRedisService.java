package com.lsy.test.redis.service.impl;


import com.lsy.test.redis.utils.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 非核心redis服务类
 */
@Service("noCoreRedisService")
@Primary
public class NoCoreRedisService extends AbstractRedisService {

    public NoCoreRedisService(@Autowired @Qualifier("stringRedisTemplate")StringRedisTemplate template) {
        super(new RedisHelper(template));
    }
}
