package com.study.designpatten.singleton;

/**
 * 将整个获取实例的方法同步，这样在一个线程访问这个方法时，其它所有的线程都要处于挂起等待状态，倒是避免了刚才同步访问创造出多个实例的危险，但是我只想说，
 * 这样的设计实在是糟糕透了，这样会造成很多无谓的等待
 */

public class BadSynchronizedSingleton {

    // 一个静态的实例
    private static BadSynchronizedSingleton synchronizedSingleton;

    // 私有化构造函数
    private BadSynchronizedSingleton() {
    }

    // 给出一个公共的静态方法返回一个单一实例
    public synchronized static BadSynchronizedSingleton getInstance() {
        if (synchronizedSingleton == null) {
            synchronizedSingleton = new BadSynchronizedSingleton();
        }
        return synchronizedSingleton;
    }

}