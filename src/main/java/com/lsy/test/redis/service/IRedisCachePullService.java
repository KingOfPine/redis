package com.lsy.test.redis.service;

import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * Created by  liangsongying on 2018/3/6.
 */
public interface IRedisCachePullService {
    String getPassword(String userName);
}
