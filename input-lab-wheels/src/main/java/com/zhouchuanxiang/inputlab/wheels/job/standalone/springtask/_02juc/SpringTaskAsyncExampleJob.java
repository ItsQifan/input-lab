package com.zhouchuanxiang.inputlab.wheels.job.standalone.springtask._02juc;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 线程池执行任务，不会阻塞任务！
 */
@Component
public class SpringTaskAsyncExampleJob {

    // 业务任务使用业务线程池
    @Async("businessExecutor")
    @Scheduled(fixedRate = 5000)
    public void businessTask() throws InterruptedException {
        // 耗时业务处理，不会影响其他任务
        Thread.sleep(3000);
    }

    // 报表任务使用报表线程池
    @Async("reportExecutor")
    @Scheduled(cron = "0 0 2 * * ?")  // 每天凌晨2点
    public void reportTask() {
        // 生成报表，即使耗时也不会影响日常业务任务
    }




}
