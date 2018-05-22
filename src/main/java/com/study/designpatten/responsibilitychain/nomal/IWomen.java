package com.study.designpatten.responsibilitychain.nomal;

/**
 * Created by pierre on 2018/5/22.
 */
public interface IWomen {
    //获得个人情况
    int getType();

    //获得个人请示，你要干什么？出去逛街？约会？还是看电影？
    String getRequest();
}
