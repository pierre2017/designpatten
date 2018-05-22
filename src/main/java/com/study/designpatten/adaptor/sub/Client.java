package com.study.designpatten.adaptor.sub;

/**
 * Created by pierre on 2018/5/22.
 */
public class Client {

    public static void main(String[] args) {
        //原有业务逻辑
        Target target = new ConcreteTarget();
        target.request();
        //现在增加了适配器角色之后的业务逻辑
        Target target1 = new Adapter();
        target1.request();
    }
}
