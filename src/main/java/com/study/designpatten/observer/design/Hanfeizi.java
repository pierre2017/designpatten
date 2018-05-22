package com.study.designpatten.observer.design;

import com.study.designpatten.observer.nomal.IHanfeizi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierre on 2018/5/22.
 */
public class Hanfeizi implements Observable, IHanfeizi {

    private List<Observer> observerList = new ArrayList<Observer>();

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子开始吃饭了");
        this.notifyAllObservers("韩非子在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子在娱乐");
        this.notifyAllObservers("韩非子在娱乐");
    }

    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyAllObservers(String content) {
        for (Observer o : observerList) {
            o.update(content);
        }
    }
}
