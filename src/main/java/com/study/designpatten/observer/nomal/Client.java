package com.study.designpatten.observer.nomal;

/**
 * Created by pierre on 2018/5/22.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Lisi lisi = new Lisi();
        Hanfeizi hanfeizi = new Hanfeizi();
        Spy spy = new Spy(hanfeizi, lisi, "breakfast");
        //开始启动线程，监控
        spy.start();

        Spy spy1 = new Spy(hanfeizi, lisi, "havefunx");
        spy1.start();
        //韩非子开始吃早餐了
        Thread.sleep(1000l);
        hanfeizi.haveBreakfast();
        //韩非子开始娱乐了
        Thread.sleep(1000l);
        hanfeizi.haveFun();

    }
}
