package com.lsy.test.redis.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by  liangsongying on 2018/3/6.
 */

public class RedisHelper {

    private StringRedisTemplate stringRedisTemplate;

    public RedisHelper(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                stringRedisTemplate.delete(key[0]);
            } else {
                stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     *
     * @param pattern
     */
    public void batchDel(String... pattern) {
        for (String kp : pattern) {
            stringRedisTemplate.delete(stringRedisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 取得缓存（int型）
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }


    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @param retain 是否保留值
     * @return
     */
    public String getString(String key, boolean retain) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            stringRedisTemplate.delete(key);
        }
        return value;
    }

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @param retain 是否保留
     * @return
     */
    public Object getObject(String key, boolean retain) {
        Object obj = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            stringRedisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存json对象<br>
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    public <T> T getFromJson(String key, Class<T> clazz) {
        return JSON.parseObject(stringRedisTemplate.boundValueOps(key).get(), clazz);
    }

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void set(String key, Object value, long time) {
        if (value.getClass().equals(String.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Integer.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Double.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Float.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Short.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Long.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Boolean.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }
        if (time > 0)
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);

    }

    /**
     * 将value对象以JSON格式写入缓存
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setJson(String key, Object value, long time) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新key对象field的值
     *
     * @param key   缓存key
     * @param field 缓存对象field
     * @param value 缓存对象field值
     */
    public void setJsonField(String key, String field, String value) {
        JSONObject obj = JSON.parseObject(stringRedisTemplate.boundValueOps(key).get());
        obj.put(field, value);
        stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
    }


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    public double decr(String key, double by) {
        return stringRedisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     *
     * @param key
     * @param by
     * @return
     */
    public double incr(String key, double by) {
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 获取double类型值
     *
     * @param key
     * @return
     */
    public double getDouble(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setDouble(String key, double value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setInt(String key, int value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }


    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key
     * @param map
     */
    public <T> void addMap(String key, Map<String, T> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public <T> void addMap(String key, String field, T obj) {
        stringRedisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 获取hash中的所有项
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        BoundHashOperations<String, String, String> boundHashOperations = null;
        boundHashOperations = stringRedisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 从hash中获取一个field
     * @param key
     * @param field
     * @return
     */
    public String hget(String key,String field){
        BoundHashOperations<String, String, String> boundHashOperations = null;
        boundHashOperations = stringRedisTemplate.boundHashOps(key);
        return boundHashOperations.get(field);
    }

    /**
     * 一次从Hash中获取多个字段的值
     * @param key
     * @param fieldList
     * @return
     */
    public List<String> hmget(String key,List<String> fieldList) {
        BoundHashOperations<String, String, String> boundHashOperations = null;
        boundHashOperations = stringRedisTemplate.boundHashOps(key);
        return boundHashOperations.multiGet(fieldList);
    }


//    public <T> Map<String, T> hget(String key, Class<T> clazz) {
//        BoundHashOperations<String, String, T> boundHashOperations = stringRedisTemplate.boundHashOps(key);
//        return boundHashOperations.entries();
//    }

    public void hset(String hName, String key, String value) {
        stringRedisTemplate.opsForHash().put(hName, key, value);
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     */
    public <T> void hmset(String key, Map<String, T> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }


    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) stringRedisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     * @author lh
     * @date 2016年8月10日
     */
    public void hRemove(String key, String... field) {
        BoundHashOperations<String, String, ?> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 删除map中所有以prefix开头的key的记录
     * @param key
     * @param prefix
     */
    public void hRemoveByStartWith(String key, String prefix) {
        if(StringUtils.isBlank(key)||StringUtils.isBlank(prefix)) return ;

        BoundHashOperations<String, String, ?> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        Set set = boundHashOperations.keys();
        Iterator it = set.iterator();
        List<String> removeKeyList = new ArrayList<>();
        while (it.hasNext()) {
            String keyStr = String.valueOf(it.next());
            if (keyStr.startsWith(prefix)) {
                removeKeyList.add(keyStr);
            }
        }
        if (removeKeyList.size() > 0)
            boundHashOperations.delete(removeKeyList.toArray());
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key  缓存KEY
     * @param time 失效时间(秒)
     */
    public void expire(String key, long time) {
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public void sAdd(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public void sRemove(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     *
     * @param oldkey
     * @param newkey
     */
    public void sRename(String oldkey, String newkey) {
        stringRedisTemplate.boundSetOps(oldkey).rename(newkey);
    }


    /**
     * 模糊查询keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }


    /**
     * list中添加一个条目
     *
     * @param itemList
     */
    public void ladd(String lname, List<String> itemList) {
        if (itemList == null) return;
        for (String s : itemList) {
            stringRedisTemplate.boundListOps(lname).leftPush(s);
        }
    }

    /**
     * 清理并重新设置列表
     * @param lname
     * @param itemList
     */
    public void clearAndPushToList(String lname, List<String> itemList) {
        if (itemList == null) return;
        stringRedisTemplate.boundListOps(lname).trim(1, 0);
        stringRedisTemplate.boundListOps(lname).rightPushAll(itemList.toArray(new String[]{}));
    }

    public void ladd(String lname, String value) {
        stringRedisTemplate.boundListOps(lname).leftPush(value);
    }

    /**
     * 获取list中的条目
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    public List<String> lget(String lname, int startIndex, int endIndex) {
        return stringRedisTemplate.boundListOps(lname).range(startIndex, endIndex);
    }

    /**
     * 给hash表中的某个字段增加一个增量
     * @param hname
     * @param filed
     * @param delta
     */
    public long hincrby(String hname, String filed, long delta) {
       return  stringRedisTemplate.opsForHash().increment(hname,filed,delta);
    }

    /**
     *  清理并重新设置hash的值
     * @param key
     * @param dataMap
     */
    public void clearAndPushToMap(String key, Map<String, String> dataMap) {
        stringRedisTemplate.delete(key);
        BoundHashOperations<String, String, String> boundHashOperations = null;
        boundHashOperations = stringRedisTemplate.boundHashOps(key);
        boundHashOperations.putAll(dataMap);
    }
}

