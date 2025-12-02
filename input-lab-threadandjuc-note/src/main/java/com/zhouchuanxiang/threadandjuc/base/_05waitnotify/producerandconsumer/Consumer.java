package com.zhouchuanxiang.threadandjuc.base._05waitnotify.producerandconsumer;

/**
 * 模拟消费者
 */
public class Consumer implements Runnable{

    public Consumer() {
    }

    public Consumer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    private Warehouse warehouse;

    @Override
    public void run() {

        while (true){
            synchronized (warehouse){
                if(warehouse.getList().size() == 0 ){
                    try {
                        System.out.println("当前库存为0，等待生产。。");
                        warehouse.wait();
                        // 被唤醒后重新进入下一轮循环，再次检查条件
                        continue;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("消费前库存："+warehouse.getList().size());
                warehouse.getOne();
                System.out.println("===消费后库存："+warehouse.getList().size());

                warehouse.notifyAll();
            }
        }


    }
}
