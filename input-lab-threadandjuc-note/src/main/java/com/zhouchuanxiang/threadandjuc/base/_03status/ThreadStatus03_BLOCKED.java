package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程状态  BLOCKED
 * 阻塞状态
 */
public class ThreadStatus03_BLOCKED {

    //**************************** begin **********************************************
    //A thread that is blocked waiting for a monitor lock is in this state.
    //等待获取锁的状态

    //****************************** end ********************************************

    private static final Object object=new Object();

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.State.BLOCKED);

        System.out.println("-----------------------");


        //线程1执行后 会持有锁5秒
        Thread thread=new Thread(new MyThead());
        thread.start();
        System.out.println("thread status="+thread.getState());


        //线程2执行后 会尝试获取锁，但是线程1持有了锁，所以线程2会进入BLOCKED状态
        Thread thread2=new Thread(new MyThead2());
        thread2.start();
        Thread.sleep(10);//目的是为了让线程2先启动
        System.out.println("thread2 status="+thread2.getState());




    }



    static class MyThead implements Runnable{
        @Override
        public void run() {
            synchronized (object){
                System.out.println("MyThead.run");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }



    static class MyThead2 implements Runnable{
        @Override
        public void run() {
            synchronized (object){
                System.out.println("MyThead2.run");

            }
        }
    }

}


