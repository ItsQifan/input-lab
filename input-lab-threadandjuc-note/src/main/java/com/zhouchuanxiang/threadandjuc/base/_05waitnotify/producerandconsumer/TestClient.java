package com.zhouchuanxiang.threadandjuc.base._05waitnotify.producerandconsumer;

/**
 * 测试类
 */
public class TestClient {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        Thread p = new Thread(new Producer(warehouse));
        p.start();

        Thread c = new Thread(new Consumer(warehouse));
        c.start();
    }
}
