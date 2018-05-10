package com.study.designpatten.factorymethod.mulityfactorymethod;

import com.study.designpatten.factorymethod.BlackHuman;
import com.study.designpatten.factorymethod.Human;

/**
 * Created by pierre on 2018/5/10.
 */
public class BlackHumanFactory extends AbstractHumanFactory {

    @Override
    public Human createHuman() {
        return new BlackHuman();
    }
}
