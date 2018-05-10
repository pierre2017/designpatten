package com.study.designpatten.simplefactory;

import com.study.designpatten.factorymethod.Human;

import java.io.Serializable;

/**
 * Created by pierre on 2018/5/9.
 */
public class HumanFactory implements Serializable {
    public static <T extends Human> T createHuman(Class<T> c) {
        Human human = null;
        try {
            human = c.newInstance();
        } catch (Exception e) {
            System.out.println("人种生产错误");
        }
        return (T) human;
    }
}
