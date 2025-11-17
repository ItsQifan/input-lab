package com.zhouchuanxiang.inputlab.utils.util;

/**
 * @ClassName StopWatchTest
 * @Description 测试类
 * @Author
 * @Date 2025/11/17 14:18
 * @Version 1.0
 */
public class StopWatchTest {
    public static void main(String[] args) throws Exception{

        //单个任务
        StopWatchUtil stopWatch = new StopWatchUtil();

        // 1. 单任务计时示例
        stopWatch.start();

        // 模拟业务操作
        Thread.sleep(1500);

        stopWatch.pause();
        System.out.println("暂停时已耗时1：" +stopWatch.getTimeMillis());

        // 模拟暂停期间的操作
        Thread.sleep(500);

        stopWatch.resume();
        System.out.println("暂停时已耗时2：" +stopWatch.getTimeMillis());
        Thread.sleep(1000);

        long totalTime = stopWatch.stop();
        System.out.println("totalTime=" + totalTime);
        System.out.println("总耗时（秒）：" + stopWatch.getTimeSeconds());


        // 2. 多任务计时示例
        stopWatch.reset();
        stopWatch.startTask("初始化");
        Thread.sleep(800);
        stopWatch.stopTask();

        stopWatch.startTask("处理数据");
        Thread.sleep(2300);
        stopWatch.stopTask();

        stopWatch.startTask("保存结果");
        Thread.sleep(500);
        stopWatch.stopTask();

        stopWatch.printTaskStats();
        System.out.println("处理数据耗时：" + stopWatch.getTaskTimeMillis("处理数据") + " ms");
    }
}
