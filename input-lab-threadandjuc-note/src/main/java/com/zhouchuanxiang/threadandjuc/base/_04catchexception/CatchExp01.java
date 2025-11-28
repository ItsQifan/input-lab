package com.zhouchuanxiang.threadandjuc.base._04catchexception;

/**
 * 测试主线程是否可以捕获子线程的异常 -- 下面方式无法获取
 */
public class CatchExp01 implements Runnable {

    public static void main(String[] args)  throws InterruptedException {
        try {
            new Thread(new CatchExp01(), "MyThread-1").start();
            Thread.sleep(1000);
            new Thread(new CatchExp01(), "MyThread-2").start();
            Thread.sleep(1000);
            new Thread(new CatchExp01(), "MyThread-3").start();
        } catch (Exception e) {
            System.out.println("主线程抛出异常.  ");
        }

    }

    @Override
    public void run() {
        try {
            throw new Exception("我来抛出异常 看看对别人有影响没!");
        } catch (Exception e) {
            System.out.println("子线程抛出异常.");
            throw new RuntimeException(e.getMessage());
        }
    }
}
