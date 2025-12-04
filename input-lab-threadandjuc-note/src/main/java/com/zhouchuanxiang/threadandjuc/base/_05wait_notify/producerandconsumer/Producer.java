package com.zhouchuanxiang.threadandjuc.base._05wait_notify.producerandconsumer;

/**
 * 模拟生产者
 */
public class Producer implements Runnable {

    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Producer() {
    }

    private Warehouse warehouse;


    @Override
    public void run() {

        //判断目前的库存
        while (true) {
            synchronized (warehouse) {
                if (warehouse.getList().size() == warehouse.getTotal()) {
                    System.out.println("仓库满了暂停生产,当前库存：" + warehouse.getList().size());
                    try {
                        warehouse.wait();
                        // 被唤醒后进入下一轮循环，再次检查库存是否已小于总量
                        continue;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    warehouse.addOne();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("===生产完毕,当前库存：" + warehouse.getList().size());

                warehouse.notifyAll();
            }
        }


    }
}
