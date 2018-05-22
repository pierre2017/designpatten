package com.study.designpatten.observer.design;


/**
 * Created by pierre on 2018/5/22.
 */
public interface Observable {
    //增加一个观察者
    void addObserver(Observer observer);

    //删除一个观察者
    void deleteObserver(Observer observer);

    //通知所有观察者
    void notifyAllObservers(String content);
}
