package com.lsy.test.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 基于Sentinel机制的redis配置类
 * 没有用到这个类
 */
//@Configuration
//@Profile(value = {"uat", "prod"})
public class SentinelRedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(SentinelRedisConfig.class);

    /**
     * 非核心缓存
     */
    @Value("${nocore.sentinel.master}")
    private String noCoreMasterName;
    @Value("${nocore.sentinel.nodes}")
    private String noCoreSentinelNodes;
    @Value("${nocore.sentinel.password}")
    private String noCoreSentinelPassword;


    /**
     * 核心缓存
     */
    @Value("${core.sentinel.master}")
    private String coreMasterName;
    @Value("${core.sentinel.nodes}")
    private String coreSentinelNodes;
    @Value("${core.sentinel.password}")
    private String coreSentinelPassword;


    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMaxTotal(1024);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setMaxWaitMillis(3000);
        return jedisPoolConfig;
    }


    /***************
     * 非核心缓存
     **************
     */

    @Bean("redisConnectionFactory")
    @Primary
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig poolConfig) {
        logger.info("noCoreSentinelNodes {}", noCoreSentinelNodes);
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();

        Set<RedisNode> sentinelNodeSet = new HashSet<>();
        String[] sentinelItemArray = noCoreSentinelNodes.split(",");
        for (String sentinelItem : sentinelItemArray) {
            String[] sentinelInfoArray = sentinelItem.split(":");
            sentinelNodeSet.add(new RedisNode(sentinelInfoArray[0].trim(), Integer.parseInt(sentinelInfoArray[1].trim())));
        }
//        这个值要和Sentinel中指定的master的值一致，不然启动时找不到Sentinel会报错的
        sentinelConfiguration.setMaster(noCoreMasterName);
        sentinelConfiguration.setSentinels(sentinelNodeSet);

        JedisConnectionFactory factory = new JedisConnectionFactory(sentinelConfiguration, poolConfig);
        factory.setPassword(noCoreSentinelPassword);
        return factory;
    }


    @Bean
    @Primary
    public RedisTemplate redisTemplate(@Autowired @Qualifier("redisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return getRedisTemplate(connectionFactory);
    }
    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate(@Autowired @Qualifier("redisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return getStringRedisTemplate(connectionFactory);
    }


    /***************
     * 核心缓存
     **************
     */
    @Bean("coreRedisConnectionFactory")
    public RedisConnectionFactory coreRedisConnectionFactory(JedisPoolConfig poolConfig) {
        logger.info("coreSentinelNodes {}", coreSentinelNodes);
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();

        Set<RedisNode> sentinelNodeSet = new HashSet<>();
        String[] sentinelItemArray = coreSentinelNodes.split(",");
        for (String sentinelItem : sentinelItemArray) {
            String[] sentinelInfoArray = sentinelItem.split(":");
            sentinelNodeSet.add(new RedisNode(sentinelInfoArray[0], Integer.parseInt(sentinelInfoArray[1])));
        }
//        这个值要和Sentinel中指定的master的值一致，不然启动时找不到Sentinel会报错的
        sentinelConfiguration.setMaster(coreMasterName);
        sentinelConfiguration.setSentinels(sentinelNodeSet);

        JedisConnectionFactory factory = new JedisConnectionFactory(sentinelConfiguration, poolConfig);
        factory.setPassword(coreSentinelPassword);
        return factory;
    }

    @Bean
    public RedisTemplate coreRedisTemplate(@Autowired @Qualifier("coreRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return getRedisTemplate(connectionFactory);
    }
    @Bean
    public StringRedisTemplate coreStringRedisTemplate(@Autowired @Qualifier("coreRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return getStringRedisTemplate(connectionFactory);
    }





    private StringRedisTemplate getStringRedisTemplate( RedisConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);

        return template;
    }
    private RedisTemplate getRedisTemplate( RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();

        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);


        return template;
    }

}
