package com.study.designpatten.strategy.design;

/**
 * Created by pierre on 2018/5/22.
 */
public class Client {

    public static void main(String[] args) {
        Strategy strategy = new ConcreteStragyOne();
        //声明上下文对象
        Content content = new Content(strategy);
        //执行封装后的方法
        content.doAnything();
    }

}
