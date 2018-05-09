package com.study.designpatten.factorymethod;

/**
 * Created by pierre on 2018/5/9.
 */
public class HumanFactory extends AbstractHumanFactory {
    @Override
    public <T extends Human> T createHuman(Class<T> c) {
        //定义一个生产的人种
        Human human = null;
        try {
            //产生一个人种
            human = c.newInstance();
        } catch (Exception e) {
            System.out.println("人种生成错误！ ");
        }
        return (T) human;
    }
}
