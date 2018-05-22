package com.study.designpatten.observer.javadesign;

/**
 * Created by pierre on 2018/5/22.
 */
public class Client {
    public static void main(String[] args) {
        Lisi lisi = new Lisi();
        Hanfeizi hanfeizi = new Hanfeizi();
        hanfeizi.addObserver(lisi);
        hanfeizi.haveBreakfast();
        hanfeizi.haveFun();
    }
}
