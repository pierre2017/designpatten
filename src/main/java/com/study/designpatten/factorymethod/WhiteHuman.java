package com.study.designpatten.factorymethod;

/**
 * Created by pierre on 2018/5/9.
 * 白色人种
 */
public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("白色人种的皮肤都是白色的");
    }

    @Override
    public void talk() {
        System.out.println("白色人种会说话，但是一般都是单字节的");
    }
}
