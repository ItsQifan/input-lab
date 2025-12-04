package com.zhouchuanxiang.threadandjuc.base._06join_yield;

/**
 * join 关键字的例子
 * join 的作用：让当前线程（通常是主线程）等待调用join()方法的线程执行完毕，然后再继续执行。
 * ——谁调用join方法，谁喊：“等等我”
 */
public class JoinExample {

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
