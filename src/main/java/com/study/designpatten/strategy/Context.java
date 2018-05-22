package com.study.designpatten.strategy;

/**
 * Created by pierre on 2018/5/22.
 */
public class Context {

    private Istrategy strategy;

    public Context(Istrategy strategy) {
        this.strategy = strategy;
    }

    //开始使用计谋了
    public void operate() {
        this.strategy.operate();
    }
}
