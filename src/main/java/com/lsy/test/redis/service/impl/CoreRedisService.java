package com.lsy.test.redis.service.impl;

import com.lsy.test.redis.utils.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 核心redis服务类
 * Created by  liangsongying on 2018/3/6.
 */
@Service("coreRedisService")
public class CoreRedisService extends AbstractRedisService {
    public CoreRedisService(@Autowired @Qualifier("coreStringRedisTemplate") StringRedisTemplate template) {
        super(new RedisHelper(template));
    }
}
