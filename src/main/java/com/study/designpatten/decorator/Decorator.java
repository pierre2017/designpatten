package com.study.designpatten.decorator;

/**
 * Created by pierre on 2018/5/22.
 */
public class Decorator implements Component {

    protected Component component;

    public Decorator(Component component) {
        super();
        this.component = component;
    }

    @Override
    public void method() {
        component.method();
    }
}
