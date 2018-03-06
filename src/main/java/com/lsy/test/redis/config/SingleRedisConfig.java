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
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class SingleRedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(SingleRedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password = null;


    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setMaxWaitMillis(3000);
        return jedisPoolConfig;
    }

    @Bean("redisConnectionFactory")
    @Primary
    JedisConnectionFactory redisConnectionFactory() {
        logger.info("redisconfig host:{}", host);
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setTimeout(timeout);
        factory.setPort(port);
        if (password != null && password.trim().length() > 0)
            factory.setPassword(password);
        factory.setPoolConfig(jedisPoolConfig());
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
        return redisConnectionFactory();
    }

    @Bean
    public RedisTemplate coreRedisTemplate(@Autowired @Qualifier("coreRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return redisTemplate(connectionFactory);
    }

    @Bean
    public StringRedisTemplate coreStringRedisTemplate(@Autowired @Qualifier("coreRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        return stringRedisTemplate(connectionFactory);
    }


    private StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);

        return template;
    }

    private RedisTemplate getRedisTemplate( RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);

        return template;
    }


}
