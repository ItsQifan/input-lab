package com.zhouchuanxiang.threadandjuc.base._02interrupt;

/**
 * 中断线程-- 不推荐用 thread方式
 */
public class Base03InterruptThreadByVolatile extends Thread {
    private volatile boolean stopped = false;

    @Override
    public void run() {
        while (!stopped) {
            // 执行任务
            System.out.println("Working...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
            }
        }
        System.out.println("Thread stopped gracefully");
    }

    public void stopThread() {
        this.stopped = true;
        this.interrupt(); // 如果线程在sleep，需要中断来立即唤醒
    }
}