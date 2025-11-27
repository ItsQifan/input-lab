package com.zhouchuanxiang.threadandjuc.base._01create;

/**
 * @author zhouchuanxiang
 * @date 2025-11-27
 * @Description 创建线程的方式
 */
public class Base01CreateThread {

    //**************************** begin **********************************************
    //两种本质都是 所有方式最终都要创建Thread对象并调用start()方法。

    //****************************** end ********************************************



    /**
     * 创建方式1：继承thread类 重写run
     * 缺点：由于Java是单继承，继承了Thread后就不能再继承其他类，限制了扩展性。
     */
    static class Way01 extends Thread {

        @Override
        public void run() {
            System.out.println("线程01执行");
        }

    }


    /**
     * 方式2：实现runnable接口
     */
    static class Way02 implements Runnable {
        @Override
        public void run() {
            System.out.println("线程02执行");
        }
    }

    public static void main(String[] args) {
        // 方式1
        Way01 way01 = new Way01();
        way01.start();

        // 方式2
        Thread thread=new Thread(new Way02());
        thread.start();

        System.out.println("主线程执行");

    }

}
