package com.lsy.test.redis.service.impl;



import com.lsy.test.redis.service.IRedisService;
import com.lsy.test.redis.utils.RedisHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by  liangsongying on 2018/3/6.
 */
public abstract class AbstractRedisService implements IRedisService {

    private RedisHelper redisHelper;


    public AbstractRedisService(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    /**
     * SET
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public String set(String key, String value) {
        redisHelper.set(key, value, -1);
        return redisHelper.getString(key);
    }

    @Override
    public String set(String key, String value, long timeout) {
        redisHelper.set(key, value, timeout);
        return redisHelper.getString(key);
    }

    /**
     * GET
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return redisHelper.getString(key);
    }

    /**
     * hget
     *
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hget(String key, String field) {
        return redisHelper.hget(key,field);
    }



    /**
     * 往hash表中设置一个条目
     *
     * @param hName
     * @param key
     * @param value
     * @return
     */
    @Override
    public void hset(String hName, String key, String value) {
         redisHelper.hset(hName,key,value);
    }

    /**
     * hmget
     *
     * @param key
     * @param fields
     * @return
     */
    @Override
    public List<String> hmget(String key, String... fields) {
        if (key == null) return null;
        if(fields==null||fields.length==0) return new ArrayList<>();
        return redisHelper.hmget(key, Arrays.asList(fields));
    }


    /**
     * 获取hash中的所有项
     *
     * @param key
     * @return
     */
    @Override
    public Map<String, String> hgetAll(String key) {
        if(key==null) return null;
        return redisHelper.hgetAll(key);
    }

    @Override
    public boolean hmset(String key, Map<String, String> map) {
        redisHelper.hmset(key, map);
        return true;
    }

    /**
     * delete
     *
     * @param key
     * @return
     */
    @Override
    public void del(String key) {
        redisHelper.del(key);
    }

    /**
     * 往list中插入一个条目
     *
     * @param itemList
     */
    @Override
    public void lmset(String lname,List<String> itemList) {
        redisHelper.ladd(lname, itemList);
    }

    /**
     * 清理并重新设置列表
     *
     * @param lname
     * @param itemList
     */
    @Override
    public void clearAndPushToList(String lname, List<String> itemList) {
        redisHelper.clearAndPushToList(lname,itemList);
    }

    /**
     * 从list中读取数据
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    @Override
    public List<String> lget(String lname,int startIndex, int endIndex) {
        return redisHelper.lget(lname,startIndex,endIndex);
    }

    /**
     * 给hash表中的某个字段增加一个增量
     *
     * @param hname
     * @param filed
     * @param delta
     */
    @Override
    public long hincrby(String hname, String filed, long delta) {
        return redisHelper.hincrby(hname,filed,delta);
    }

    /**
     * 清理并重新设置hash的值
     *
     * @param key
     * @param dataMap
     */
    @Override
    public void clearAndPushToMap(String key, Map<String, String> dataMap) {
         redisHelper.clearAndPushToMap(key,dataMap);
    }

    /**
     * 移除hash中的field
     *
     * @param key
     * @param fields
     */
    @Override
    public void hremove(String key, String ... fields) {
        redisHelper.hRemove(key,fields);
    }

    /**
     * 移除hash中以prefix开头的记录
     *
     * @param key
     * @param prefix
     */
    @Override
    public void hRemoveByStartWith(String key, String prefix) {
        redisHelper.hRemoveByStartWith(key,prefix);
    }
}
