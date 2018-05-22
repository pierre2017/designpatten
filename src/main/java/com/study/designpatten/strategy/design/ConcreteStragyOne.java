package com.study.designpatten.strategy.design;

/**
 * Created by pierre on 2018/5/22.
 */
public class ConcreteStragyOne implements Strategy {
    @Override
    public void dosomething() {
        System.out.println("具体策略1的运算法则");
    }
}
