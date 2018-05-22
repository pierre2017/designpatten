package com.study.designpatten.strategy.design;

/**
 * Created by pierre on 2018/5/22.
 */
public class ConcreteStragyTwo implements Strategy {
    @Override
    public void dosomething() {
        System.out.println("策略2的运算法则");
    }
}
