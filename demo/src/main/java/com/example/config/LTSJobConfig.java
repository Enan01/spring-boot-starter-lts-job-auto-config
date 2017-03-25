package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Enan on 17/3/25.
 */
@Configuration
// 需要扫描spring-boot-starter-lts-job-auto-config包中的@JobRunner4TaskTracker 注解类
// 因为配置了 @EnableTaskTracker 这个注解，会自动获取容器中带 @JobRunner4TaskTracker 注解的类
@ComponentScan("spring.boot.lts.job")
public class LTSJobConfig {
}
