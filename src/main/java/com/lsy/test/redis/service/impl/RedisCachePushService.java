package com.lsy.test.redis.service.impl;




import com.lsy.test.redis.service.IRedisCachePushService;
import com.lsy.test.redis.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * redis缓存数据推送服务，包括核心和非核心
 * Created by  liangsongying on 2018/3/6.
 */
@Service
public class RedisCachePushService implements IRedisCachePushService {

    @Autowired
    @Qualifier("noCoreRedisService")
    private IRedisService noCoreRedisService;

    @Override
    public boolean putPassword(String username, String password) {
       String value= noCoreRedisService.set(username,password);
        return value!=null;
    }

//    private static Logger logger = LoggerFactory.getLogger(RedisCachePushService.class);



}
