package com.study.designpatten.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by pierre on 2018/5/4.
 */
public class MyInvokeHandler implements InvocationHandler {

    private Object object;

    public MyInvokeHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxy, args);
    }
}
