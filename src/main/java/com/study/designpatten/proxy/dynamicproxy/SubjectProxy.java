package com.study.designpatten.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;

/**
 * Created by pierre on 2018/5/4.
 */
public class SubjectProxy extends MyDynamicProxy {

    public static <T> T newProxyInstant(Object object) {

        ClassLoader classLoader = object.getClass().getClassLoader();

        Class<?>[] interfaces = object.getClass().getInterfaces();

        InvocationHandler invocationHandler = new MyInvokeHandler(object);

        return newProxyInstant(classLoader, interfaces, invocationHandler);

    }
}
