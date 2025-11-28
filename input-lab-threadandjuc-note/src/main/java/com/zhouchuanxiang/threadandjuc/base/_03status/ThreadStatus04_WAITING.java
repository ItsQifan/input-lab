package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程状态  WAITING
 * 无期限等待 状态
 */
public class ThreadStatus04_WAITING {

    //**************************** begin **********************************************
    //A thread that is waiting indefinitely for another thread to perform a particular action is in this state.
    //场景： 线程使用了锁，且调用了wait()方法。此时，会进入WAITING状态。

    //****************************** end ********************************************

    private static final Object object=new Object();

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.State.WAITING);

        System.out.println("-----------------------");


        Thread thread=new Thread(new MyThead());
        thread.start();

        Thread.sleep(100);

        System.out.println(thread.getState());//--WAITING




    }


    static class MyThead implements Runnable{
        @Override
        public void run() {
            synchronized (object){
                System.out.println("MyThead.run");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("MyThead.run222");
            }
        }
    }

}


