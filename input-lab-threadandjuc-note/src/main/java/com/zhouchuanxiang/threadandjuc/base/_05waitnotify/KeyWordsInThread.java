package com.zhouchuanxiang.threadandjuc.base._05waitnotify;

/**
 * 关键字
 * 多线程wait notify notifyAll join sleep yield作用与方法详细解读
 */
public class KeyWordsInThread {

    //**************************** begin **********************************************
    //! 1.wait() notify() notifyall() 方法是Object的本地final方法 无法被重写
    //   ! 2.wait() 使当前的线程阻塞 前提是必须获取到锁 一般配合synchronized 关键字使用 即一般在synchronized里面 使用wait notify notifyall
    //   ! 3.由于wait() notify() notifyall() 在synchronized里面执行 那么说明 当前线程一定是获取锁了
    //   ! 4. 当线程执行wait的时候会释放当前锁让出CPU资源进入等待状态
    //   ! 5. 当执行notify()/notifyall()的时候会唤醒一个或者多个处于正在等待的线程 然后继续执行知道执行完毕synchronized或者再次遇到wait
    //**************************** end **********************************************
}
