package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程的所有状态
 */
public class ThreadStatus00All {

    //**************************** begin **********************************************
    //

    //****************************** end ********************************************

    public static void main(String[] args) {

        // 线程的所有状态

        System.out.println(Thread.State.NEW);
        System.out.println(Thread.State.RUNNABLE);
        System.out.println(Thread.State.BLOCKED);
        System.out.println(Thread.State.TIMED_WAITING);
        System.out.println(Thread.State.TERMINATED);

        System.out.println("-----------------------");


        // 线程的状态是NEW，因为还没有调用start方法
        Thread thread=new Thread(new MyThead());
        System.out.println("状态1："+thread.getState());

        // 线程的状态是RUNNABLE，因为已经调用了start方法
        thread.start();
        System.out.println("状态2："+thread.getState());





    }



    static class MyThead implements Runnable{
        @Override
        public void run() {
            System.out.println("MyThead.run");
        }
    }


}


