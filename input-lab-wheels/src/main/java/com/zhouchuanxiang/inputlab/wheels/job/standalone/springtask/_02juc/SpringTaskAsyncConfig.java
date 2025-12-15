package com.zhouchuanxiang.inputlab.wheels.job.standalone.springtask._02juc;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync//开启异步
@EnableScheduling  // 启用 spring task 定时任务支持
public class SpringTaskAsyncConfig {

    @Bean("businessExecutor")
    public ThreadPoolTaskExecutor businessExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("business-");
        return executor;
    }

    @Bean("reportExecutor")
    public ThreadPoolTaskExecutor reportExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("report-");
        return executor;
    }

    /**
     * 应用关闭时优雅地关闭线程池
     */
    @PreDestroy
    public void shutdown() {
        // 应用关闭时优雅地关闭线程池
        businessExecutor().shutdown();
    }


    //spring task优缺点：
    //优点：简单
    //缺点：所有 @Scheduled 任务在一个单线程中执行！ 当一个任务执行时间过长时，会影响其他任务的执行。

    // 解决方法：加入线程池
    //


    // * 注意事项：
    // * 1-执行任务锁
    // * 2-设计可重跑
    // * 3-记录日志【执行时间 / 执行状态 / 】
    // * 4-finally 块，记录日志，删除锁

}
