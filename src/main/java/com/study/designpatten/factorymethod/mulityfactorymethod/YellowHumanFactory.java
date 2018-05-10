package com.study.designpatten.factorymethod.mulityfactorymethod;

import com.study.designpatten.factorymethod.Human;
import com.study.designpatten.factorymethod.YellowHuman;

/**
 * Created by pierre on 2018/5/10.
 */
public class YellowHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createHuman() {
        return new YellowHuman();
    }
}
