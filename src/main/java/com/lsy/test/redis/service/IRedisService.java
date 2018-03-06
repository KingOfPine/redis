package com.lsy.test.redis.service;

import java.util.List;
import java.util.Map;

public interface IRedisService {
	
	/**
	 * SET
	 * @param key
	 * @param value
	 * @return
	 */
	 String set(String key, String value);

	String set(String key, String value, long timeout);;
	
	/**
	 * GET
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * hget
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);

	
	/**
	 * hmget
	 * @param key
	 * @param fields
	 * @return
	 */
	List<String> hmget(String key, String... fields);

	/**
	 * 获取hash中的所有项
	 * @param key
	 * @return
	 */
	Map<String, String> hgetAll(String key);

	/**
	 * 一次往hash设置多个条目
	 * @param key
	 * @param map
	 * @return
	 */
	boolean hmset(String key, Map<String, String> map);

	/**
	 * 往hash表中设置一个条目
	 * @param hName
	 * @param key
	 * @param value
	 * @return
	 */
	void hset(String hName, String key, String value);

	/**
	 * delete
	 * @param key
	 * @return
	 */
	void del(String key);

	/**
	 * 往list中插入多个条目
	 * @param value
	 */
	void  lmset(String lname, List<String> itemList);

	/**
	 * 清理并重新设置列表
	 * @param lname
	 * @param itemList
	 */
	void clearAndPushToList(String lname, List<String> itemList);

	/**
	 * 从list中读取数据
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	List<String> lget(String lname, int startIndex, int endIndex);

	/**
	 * 给hash表中的某个字段增加一个增量
	 * @param hname
	 * @param filed
	 * @param delta
	 */
	long hincrby(String hname, String filed, long delta);


	/**
	 * 清理并重新设置hash的值
	 * @param key
	 * @param dataMap
	 */
    void clearAndPushToMap(String key, Map<String, String> dataMap);


	/**
	 * 移除hash中的field
	 * @param key
	 * @param fields
	 */
	void hremove(String key, String... fields);

	/**
	 * 移除hash中以prefix开头的记录
	 * @param key
	 * @param prefix
	 */
	void hRemoveByStartWith(String key, String prefix);
}
