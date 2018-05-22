package com.study.designpatten.observer.design;

/**
 * Created by pierre on 2018/5/22.
 */

public interface Observer {

    //一发现别人有动静，自己也要行动起来
    void update(String content);

}
