package com.study.designpatten.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by pierre on 2018/5/4.
 */
public class MyDynamicProxy<T> {


    public static <T> T newProxyInstant(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
        return (T) Proxy.newProxyInstance(loader, interfaces, h);
    }
}
