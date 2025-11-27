package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程状态  RUNNABLE
 */
public class ThreadStatus02_RUNNABLE {

    //**************************** begin **********************************************
    //完成创建，且完成调用start() 方法时，状态时 RUNNABLE

    //****************************** end ********************************************

    public static void main(String[] args) {


        System.out.println(Thread.State.RUNNABLE);

        System.out.println("-----------------------");


        Thread thread=new Thread(new MyThead());

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


