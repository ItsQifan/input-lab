package com.zhouchuanxiang.inputlab.wheels.job.standalone.springtask._01simple;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling  // 启用 spring task 定时任务支持
public class SpringTaskSimpleConfig {


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
