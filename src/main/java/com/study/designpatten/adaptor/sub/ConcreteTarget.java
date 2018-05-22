package com.study.designpatten.adaptor.sub;

/**
 * Created by pierre on 2018/5/22.
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("if you need any help,please call me");
    }
}
