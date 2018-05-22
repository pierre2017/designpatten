package com.study.designpatten.strategy.design;

/**
 * Created by pierre on 2018/5/22.
 */
public class Content {
    //抽象策略
    private Strategy strategy;

    public Content(Strategy strategy) {
        this.strategy = strategy;
    }

    //封装后的策略方法
    public void doAnything() {
        this.strategy.dosomething();
    }
}
