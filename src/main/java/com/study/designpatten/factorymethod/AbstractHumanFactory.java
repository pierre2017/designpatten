package com.study.designpatten.factorymethod;

/**
 * Created by pierre on 2018/5/9.
 */
public abstract class AbstractHumanFactory {

    public abstract <T extends Human> T createHuman(Class<T> c);
}
