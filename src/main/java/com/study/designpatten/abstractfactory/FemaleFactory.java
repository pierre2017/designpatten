package com.study.designpatten.abstractfactory;

/**
 * Created by pierre on 2018/5/10.
 */
public class FemaleFactory implements HumanFactory {

    @Override
    public Human createYellowHuman() {
        return new FemaleYellowHuamn();
    }

    @Override
    public Human createWhiteHuman() {
        return null;
    }

    @Override
    public Human createBlackHuman() {
        return null;
    }
}
