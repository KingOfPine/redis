package com.lsy.test.redis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

//import org.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Created by liangsongying on 2017/10/30.
 */
@Configuration
public class MybatisConfig {


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        StringBuilder mapperPackageBuilder = new StringBuilder();
        mapperPackageBuilder.append("com.lsy.test.redis.mapper");

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(mapperPackageBuilder.toString());
        Properties properties = new Properties();
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
