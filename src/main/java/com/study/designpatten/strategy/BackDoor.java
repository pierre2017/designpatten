package com.study.designpatten.strategy;

/**
 * Created by pierre on 2018/5/22.
 * 乔国老开后门
 */
public class BackDoor implements Istrategy {
    @Override
    public void operate() {
        System.out.println("找乔国老帮忙， 让吴国太给孙权施加压力");
    }
}
