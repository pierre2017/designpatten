package com.study.designpatten.abstractfactory;

/**
 * Created by pierre on 2018/5/10.
 */
public interface Human {
    //每个人种都有相应的颜色
    public void getColor();

    //人类会说话
    public void talk();

    //每个人都有性别
    public void getSex();
}
