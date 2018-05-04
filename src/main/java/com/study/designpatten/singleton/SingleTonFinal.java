package com.study.designpatten.singleton;

/**
 * 单例最终版本
 */
public class SingleTonFinal {

    private SingleTonFinal() {
    }

    public static SingleTonFinal getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        static SingleTonFinal instance = new SingleTonFinal();
    }
}
