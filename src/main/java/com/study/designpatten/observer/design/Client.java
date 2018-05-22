package com.study.designpatten.observer.design;

/**
 * Created by pierre on 2018/5/22.
 */
public class Client {

    public static void main(String[] args) {
        Observer lisi = new Lisi();

        Observer wangsi = new WangSi();

        Hanfeizi hanfeizi = new Hanfeizi();

        hanfeizi.addObserver(lisi);

        hanfeizi.addObserver(wangsi);

        hanfeizi.haveBreakfast();
    }

}
