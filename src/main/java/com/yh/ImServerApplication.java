package com.yh;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@MapperScan(value = ImServerApplication.COMPONENT_SCAN, annotationClass = Mapper.class)
@SpringBootApplication(scanBasePackages = ImServerApplication.COMPONENT_SCAN)
public class ImServerApplication {
    public static final String COMPONENT_SCAN = "com.yh.imserver";

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }

}
