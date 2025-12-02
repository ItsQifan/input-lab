package com.zhouchuanxiang.threadandjuc.base._05waitnotify.producerandconsumer;

import lombok.Data;

import java.util.Date;
import java.util.LinkedList;

/**
 * 模拟仓库
 */
@Data
public class Warehouse {
    //仓库最大容量10个 多了放不下
    private final int total=10;

    private LinkedList<Object> list=new LinkedList<>();


    public void addOne(){
        //add是尾部添加
//        list.add(new Date());
        //push是压栈，头部添加
        list.push(new Date());
    }

    public void getOne(){
        //pop是移除 移除头部第一个
        //remove是移除 移除头部第一个
        //poll是移除 移除头部第一个

        //pollLast 移除最后一个
        list.pollLast();
    }


}
