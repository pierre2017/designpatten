package com.study.designpatten.singleton;

/**
 * 其实我们同步的地方只是需要发生在单例的实例还未创建的时候，在实例创建以后，获取实例的方法就没必要再进行同步控制了，
 * 所以我们将上面的示例改为很多教科书中标准的单例模式版本，也称为双重加锁。
 * <p>
 * 假设我们去掉同步块中的是否为null的判断，有这样一种情况，假设A线程和B线程都在同步块外面判断了synchronizedSingleton为null，
 * 结果A线程首先获得了线程锁
 * ，进入了同步块，然后A线程会创造一个实例，此时synchronizedSingleton已经被赋予了实例，A线程退出同步块，直接返回了第一个创造的实例
 * ，此时B线程获得线程锁
 * ，也进入同步块，此时A线程其实已经创造好了实例，B线程正常情况应该直接返回的，但是因为同步块里没有判断是否为null，直接就是一条创建实例的语句
 * ，所以B线程也会创造一个实例返回，此时就造成创造了多个实例的情况。
 */
public class SynchronizedSingleton {

    // 一个静态的实例
    private static SynchronizedSingleton synchronizedSingleton;

    // 私有化构造函数
    private SynchronizedSingleton() {
    }

    // 给出一个公共的静态方法返回一个单一实例
    public static SynchronizedSingleton getInstance() {
        if (synchronizedSingleton == null) {
            synchronized (SynchronizedSingleton.class) {
                if (synchronizedSingleton == null) {
                    synchronizedSingleton = new SynchronizedSingleton();
                }
            }
        }
        return synchronizedSingleton;
    }
}