package com.zhouchuanxiang.threadandjuc.base._05waitnotify.print100use2thread;

/**
 * 用两个线程交替打印1--100的奇偶
 * 【重要！思想： 一个人等，另一个人通知！！】
 */
public class Print100 {

    public static Object lock = new Object();
    public static volatile int count = 1;

    public static void main(String[] args) {

        Thread thread = new Thread(new MyRunnable1(), "奇数");
        thread.start();

        Thread thread2 = new Thread(new MyRunnable2(), "偶数");
        thread2.start();

    }


    /**
     * 奇数
     */
    static class MyRunnable1 implements Runnable {

        @Override
        public void run() {

            while (count <= 100) {
                if (count % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                    count++;
                } else {

                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }

        }
    }

    /**
     * 偶数
     */
    static class MyRunnable2 implements Runnable {

        @Override
        public void run() {
            while (count <= 100) {
                if (count % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                    count++;
                } else {

                    synchronized (lock) {
                        try {
                            lock.notify();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        }
    }


//    static class MyRunnable implements Runnable {
//
//
//        @Override
//        public void run() {
//
//            while (count <= 100) {
//
//                synchronized (lock) {
//
//                    //奇数
//                    if (count % 2 == 1) {
//                        try {
//                            System.out.println(Thread.currentThread().getName() + ":" + count);
//                            count++;
//                            lock.wait();
//
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//
//                    //偶数
//                    if (count % 2 == 0) {
//                        try {
//                            System.out.println(Thread.currentThread().getName() + ":" + count);
//                            count++;
//                            lock.notify();
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//            }
//        }
//
//    }


}
