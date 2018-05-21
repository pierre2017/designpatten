package com.study.designpatten.adaptor;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by pierre on 2018/5/21.
 * 我们希望将HashMap这个类加到观察者列表里，在被观察者产生变化时，假设我们要清空整个MAP
 */
public class HashMapObsverAdaptor<K, V> extends HashMap<K, V> implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        //被观察者变化时，清空Map
        super.clear();
    }
}
