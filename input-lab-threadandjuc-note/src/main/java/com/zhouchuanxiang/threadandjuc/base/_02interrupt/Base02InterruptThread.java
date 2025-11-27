package com.zhouchuanxiang.threadandjuc.base._02interrupt;

/**
 * 中断线程
 */
public class Base02InterruptThread {

    //**************************** begin **********************************************
    //【thread.interrupt()】 中断线程：是由主线程或其他线程调用 某线程的interrupt方法，本质是修改该线程的中断标志位为true。
    //注意！： 当线程处理了InterruptedException异常后，设置的中断标志位会被重置为false。！！！需要在catch中执行
    // Thread.currentThread().interrupt(); // 重新设置中断状态

    //****************************** end ********************************************

    static class TestThread implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("线程执行...线程状态1:" + Thread.currentThread().isInterrupted());
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    System.out.println("线程执行...线程状态2:" + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt(); // 重新设置中断状态
                    System.out.println("线程执行...线程状态3:" + Thread.currentThread().isInterrupted());
                }
                System.out.println("线程执行...线程状态4:" + Thread.currentThread().isInterrupted());
            }
            System.out.println("线程被中断");
            System.out.println("线程状态1:" + Thread.currentThread().isInterrupted());

        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new TestThread());

        thread.start();

        Thread.sleep(2000);

        thread.interrupt();

    }


}
