package com.study.designpatten.factorymethod.mulityfactorymethod;

import com.study.designpatten.factorymethod.Human;
import com.study.designpatten.factorymethod.WhiteHuman;

/**
 * Created by pierre on 2018/5/10.
 */
public class WhiteHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createHuman() {
        return new WhiteHuman();
    }
}
