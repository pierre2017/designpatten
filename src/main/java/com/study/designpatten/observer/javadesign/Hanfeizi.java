package com.study.designpatten.observer.javadesign;

import com.study.designpatten.observer.nomal.IHanfeizi;

import java.util.Observable;

/**
 * Created by pierre on 2018/5/22.
 */
public class Hanfeizi extends Observable implements IHanfeizi {
    @Override
    public void haveBreakfast() {
        System.out.println("韩非子开始吃饭了");
        super.setChanged();
        super.notifyObservers("韩非子在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子在娱乐");
        super.setChanged();
        super.notifyObservers("韩非子在娱乐");
    }
}
