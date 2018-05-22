package com.study.designpatten.strategy;

/**
 * Created by pierre on 2018/5/22.
 */
public class BlockEnemy implements Istrategy {
    @Override
    public void operate() {
        System.out.println("孙夫人断后，挡住追兵");
    }
}
