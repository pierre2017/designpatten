package com.study.designpatten.decorator;

/**
 * Created by pierre on 2018/5/22.
 */
public class ConcreteComponent implements Component {
    @Override
    public void method() {
        System.out.println("原来的方法");
    }
}
