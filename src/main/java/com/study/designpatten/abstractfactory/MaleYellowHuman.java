package com.study.designpatten.abstractfactory;

/**
 * Created by pierre on 2018/5/10.
 */
public class MaleYellowHuman extends AbstractYellowHuman {
    @Override
    public void getSex() {
        System.out.println("黄人男性");
    }
}
