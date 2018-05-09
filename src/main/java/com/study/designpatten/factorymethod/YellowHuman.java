package com.study.designpatten.factorymethod;

/**
 * Created by pierre on 2018/5/9.
 * 黄色人种
 */
public class YellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黄色人种的皮肤是黄色的！");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种说话，一般都是双字节！");
    }
}
