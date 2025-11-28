package com.zhouchuanxiang.threadandjuc.base._03status;

/**
 * 线程的所有状态
 */
public class ThreadStatus00All {

    //**************************** begin **********************************************
    //
    //转换动作	        从状态	        到状态	        说明
    //start()	        NEW	            RUNNABLE	    启动线程
    //获取CPU时间片	    RUNNABLE	    RUNNABLE	    实际执行
    //yield()	        RUNNABLE	    RUNNABLE	    让出CPU，但状态不变
    //synchronized	    RUNNABLE	    BLOCKED	        等待获取对象锁
    //wait()	        RUNNABLE	    WAITING	        无限期等待
    //wait(timeout)	    RUNNABLE	    TIMED_WAITING	限期等待
    //sleep(timeout)	RUNNABLE	    TIMED_WAITING	睡眠
    //join()	        RUNNABLE	    WAITING	        等待其他线程结束
    //join(timeout)	    RUNNABLE	    TIMED_WAITING	限期等待其他线程
    //notify()	        WAITING	        BLOCKED	        被唤醒，等待获取锁
    //超时	            TIMED_WAITING	RUNNABLE	    等待时间到期
    //中断	            WAITING/TIMED_WAITING	RUNNABLE	被中断
    //run()结束	        RUNNABLE	    TERMINATED	    线程执行完毕

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


