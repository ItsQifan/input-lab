package com.zhouchuanxiang.inputlab.utils.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xinghl
 * @date ：Created in 2020/11/18 13:45
 * @description：获取request里面的参数信息
 * @modified By：
 * @version: 1.0.0
 */
public class RequestMsgUtil<T> {

    private final static ThreadLocal<Object> requestHolder = new ThreadLocal<>();

    /**
     * 将当前线程的用户存储到ThreadLocal中
     *
     * @param vo
     */
    public static <T> void add(T vo) {
        requestHolder.set(vo);
    }

    public static <T> T get() {
        T value = (T) requestHolder.get();
        if (value == null) {
            // todo 可以根据需要返回默认值或抛出异常
            return null;
        }
        return value;
    }

    public static <T> void getSessionUser() {
        //todo
//        Object requestMsgVO = requestHolder.get();
//        if (requestMsgVO == null) {
//            requestMsgVO = new RequestMsgVO();
//            requestMsgVO.setUserName("system");
//            requestMsgVO.setSystemType("manager");
//            requestMsgVO.setUserId("-1");
//            requestMsgVO.setUser(new AuthUserVO());
//            List<String> orgIds = new ArrayList<>();
//            orgIds.add("-1");
//            return requestMsgVO;
//        }
//        return requestMsgVO;
    }

    /**
     * 在请求处理完成后，从ThreadLocal中将本次请求的相关信息清除，
     * 以免造成内存泄漏；
     */
    public static void remove() {
        requestHolder.remove();
    }


}
