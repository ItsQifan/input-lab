package com.zhouchuanxiang.threadandjuc.base._04catchexception;

/**
 * 使用UncaughtExceptionHandler捕获异常
 */
public class CatchExp02ByUncaughtExceptionHandler {


    public static void main(String[] args) {
        //设置全局的异常处理器
        MyUncaughtExceptionHandler handler=new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        Thread thread1=new Thread(new MyThread());
        thread1.start();


        Thread thread2=new Thread(new MyThread());
        thread2.start();

    }


    static class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("线程开始运行");
            //模拟异常
            System.out.println(1/0);
        }
    }

}


/**
 * 捕获多线程异常
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("线程"+t.getName()+"出现了异常,异常为："+e.getMessage());
        e.printStackTrace();

    }
}