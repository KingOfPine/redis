package com.lsy.test.redis.service.impl;


import com.lsy.test.redis.service.IRedisCachePullService;
import com.lsy.test.redis.service.IRedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis缓存拉取服务
 * Created by  liangsongying on 2018/3/6.
 */
@Service
public class RedisCachePullService implements IRedisCachePullService {
    private static Logger logger = LoggerFactory.getLogger(RedisCachePullService.class);

    @Autowired
    @Qualifier("noCoreRedisService")
    private IRedisService noCoreRedisService;

    @Autowired
    @Qualifier("coreRedisService")
    private IRedisService coreRedisService;


    @Override
    public String getPassword(String userName) {
        return noCoreRedisService.get(userName);
    }
}
