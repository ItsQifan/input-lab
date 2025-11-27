package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程的 NEW 状态
 */
public class ThreadStatus01_NEW {

    //**************************** begin **********************************************
    //线程状态：NEW
    //A thread that has not yet started is in this state.
    //创建线程完成后，线程还未启动时的状态。

    //****************************** end ********************************************

    public static void main(String[] args) {

        // 线程的所有状态

        System.out.println(Thread.State.NEW);

        System.out.println("-----------------------");


        // 线程的状态是NEW，因为还没有调用start方法
        Thread thread=new Thread(new MyThead());
        System.out.println("状态1："+thread.getState());






    }



    static class MyThead implements Runnable{
        @Override
        public void run() {
            System.out.println("MyThead.run");
        }
    }


}


