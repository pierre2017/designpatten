package com.study.designpatten.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pierre on 2018/5/4.
 * 动态代理
 */
public class DynamicProxy implements InvocationHandler {

    private Object proxy;

    public DynamicProxy(Object proxy) {
        this.proxy = proxy;
    }

    public void before() {
        System.out.println("方法运行前执行before方法");
    }

    public void after() {
        System.out.println("方法运行后执行after方法");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //假设我们切入toString方法，其他其实也是类似的，一般我们这里大部分是针对特定的方法做事情的，通常不会对类的全部方法切入
        //比如我们常用的事务管理器，我们通常配置的就是对save,update,delete等方法才打开事务
        if (method.getName().equals("toString")) {
            before();
        }
        Object result = method.invoke(proxy, args);
        if (method.getName().equals("toString")) {
            after();
        }
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
    }
}
