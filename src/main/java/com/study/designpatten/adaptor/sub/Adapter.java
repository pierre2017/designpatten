package com.study.designpatten.adaptor.sub;

/**
 * Created by pierre on 2018/5/22.
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.dosometing();
    }
}
