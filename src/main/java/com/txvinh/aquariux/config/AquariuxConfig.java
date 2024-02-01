package com.txvinh.aquariux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AquariuxConfig {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        TaskExecutor taskExecutor = new ThreadPoolTaskScheduler();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,10, 1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}
