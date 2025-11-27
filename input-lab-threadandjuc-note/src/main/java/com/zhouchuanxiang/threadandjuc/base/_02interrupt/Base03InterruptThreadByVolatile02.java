package com.zhouchuanxiang.threadandjuc.base._02interrupt;

/**
 * 中断线程 - 实现Runnable方式
 */
public class Base03InterruptThreadByVolatile02 implements Runnable {
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

    public void stopThread(Thread thread) {
        this.stopped = true;
        thread.interrupt(); // 如果线程在sleep，需要中断来立即唤醒
    }
}

/**
 * 调用 使用方式
 */
class Main{
    public static void main(String[] args) throws InterruptedException {
        Base03InterruptThreadByVolatile02 volatile02 = new Base03InterruptThreadByVolatile02();
        Thread thread=new Thread(volatile02);
        thread.start();

        thread.sleep(2000); // 让线程运行一段时间


        volatile02.stopThread(thread);


        thread.join();
        System.out.println("Main thread stopped");
    }
}