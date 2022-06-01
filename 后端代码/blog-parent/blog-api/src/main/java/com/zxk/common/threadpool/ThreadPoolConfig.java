package com.zxk.common.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 闫柯含
 * @date 2022年 01月 30日 下午 4:52
 * @功能说明: => 线程池的配置类
 */
@Configuration
@EnableAsync //开启多线程
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(4);
        //设置最大线程数 => 取决于电脑配置
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        //配置阻塞队列
        executor.setQueueCapacity(1000);
        //设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(60);
        //设置拒绝策略(如果被拒绝就抛出错误)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //初始化
        executor.initialize();
        return executor;
    }
}
