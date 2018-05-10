package com.study.designpatten.abstractfactory;

/**
 * Created by pierre on 2018/5/10.
 */
public abstract class AbstractWhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("白色人种的皮肤是白色的");
    }

    @Override
    public void talk() {
        System.out.println("白色人种会说话，说话都是单字节的");
    }
}
