package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程状态  TIMED_WAITING
 * 有期限等待 状态
 */
public class ThreadStatus05_TIMED_WAITING {

    //**************************** begin **********************************************
    //A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state.
    //场景： 一个线程执行过程中，调用了sleep方法，当前线程的状态会变为TIMED_WAITING

    //****************************** end ********************************************

    private static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.State.TIMED_WAITING);

        System.out.println("-----------------------");


        Thread thread = new Thread(new MyThead());
        thread.start();

        Thread.sleep(100);

        System.out.println(thread.getState());//--TIMED_WAITING


    }


    static class MyThead implements Runnable {
        @Override
        public void run() {
            System.out.println("MyThead.run");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("MyThead.run222");
        }
    }

}


