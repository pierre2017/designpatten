package com.study.designpatten.factorymethod;

/**
 * Created by pierre on 2018/5/9.
 * 黑色人种
 */
public class BlackHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黑色人种的皮肤是黑色的！");
    }

    @Override
    public void talk() {
        System.out.println("黑人说话一般人听不懂");
    }
}
