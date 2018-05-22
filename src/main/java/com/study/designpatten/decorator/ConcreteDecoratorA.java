package com.study.designpatten.decorator;

/**
 * Created by pierre on 2018/5/22.
 */
public class ConcreteDecoratorA extends Decorator {
    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    public void methodA() {
        System.out.println("被装饰器A扩展的功能");
    }

    public void method() {
        System.out.println("针对该方法加一层A包装");
        super.method();
        System.out.println("A包装结束");
    }
}
