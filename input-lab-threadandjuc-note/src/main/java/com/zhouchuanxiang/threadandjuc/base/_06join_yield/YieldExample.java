package com.zhouchuanxiang.threadandjuc.base._06join_yield;

/**
 * yield 关键字的例子
 * yield 的作用：用于提示线程调度器当前线程愿意让出 CPU 使用权。
 *
 */
public class YieldExample {

    public static void main(String[] args) {

        Thread t = new Thread(new MyRunnable());
        t.start();

        System.out.println("主线程。。1");
        try {
            t.join();// 主线程等待子线程执行完毕
//            t.join(2000);  // todo 只等待2秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("主线程。。2");

    }


    static class MyRunnable implements Runnable{
        @Override
        public void run() {

            System.out.println("子线程开始执行");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {


            }
            System.out.println("子线程完成执行");
        }
    }
}
