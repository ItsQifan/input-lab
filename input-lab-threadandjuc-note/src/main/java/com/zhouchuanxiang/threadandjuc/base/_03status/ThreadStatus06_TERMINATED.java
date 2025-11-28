package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程状态  TERMINATED
 * 终止 状态
 */
public class ThreadStatus06_TERMINATED {

    //**************************** begin **********************************************
    //The thread has completed execution.
    //场景： 线程执行完毕后的状态

    //****************************** end ********************************************

    private static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.State.TERMINATED);

        System.out.println("-----------------------");


        Thread thread = new Thread(new MyThead());
        thread.start();

        Thread.sleep(5000);

        System.out.println(thread.getState());//--TERMINATED


    }


    static class MyThead implements Runnable {
        @Override
        public void run() {
            System.out.println("MyThead.run");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("MyThead.run222");
        }
    }

}


