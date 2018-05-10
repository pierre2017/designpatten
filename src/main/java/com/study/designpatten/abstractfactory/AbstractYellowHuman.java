package com.study.designpatten.abstractfactory;

/**
 * Created by pierre on 2018/5/10.
 */
public abstract class AbstractYellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黄色人种的皮肤是黄色的");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种会说话，说话都是双字节的");
    }
}
