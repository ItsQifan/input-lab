package com.zhouchuanxiang.threadandjuc.base._05waitnotify.producerandconsumer;

/**
 * 测试类
 */
public class ProducerAndConsumerTestClient {

    //目标：用两个线程模拟“生产者-消费者”模型，操作同一个仓库 Warehouse。
    //规则：
    //仓库满了：生产者暂停，等消费者消费后再继续生产。
    //仓库空了：消费者暂停，等生产者生产后再继续消费。
    //关键技术点：synchronized + wait() + notifyAll()，并且所有等待和唤醒都在同一把锁 warehouse 上完成。

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        Thread p = new Thread(new Producer(warehouse));
        p.start();

        Thread c = new Thread(new Consumer(warehouse));
        c.start();
    }
}
