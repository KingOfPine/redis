package com.lsy.test.redis.service;


/**
 * Created by  liangsongying on 2018/3/6.
 */
public interface IRedisCachePushService {

    public boolean putPassword(String username, String password);


}
