package com.study.designpatten.proxy;

/**
 * 代理模式
 * 1.代理模式可以分为两种：动态代理和静态代理
 * <p>
 * staticproxy目录下为静态代理
 * 我们使用代理模式解决了上述问题，从静态代理的使用上来看，我们一般是这么做的。
 * 1，代理类一般要持有一个被代理的对象的引用。
 * 2，对于我们不关心的方法，全部委托给被代理的对象处理。
 * 3，自己处理我们关心的方法。
 * <p>
 * dynamicproxy 目录下为动态代理
 */

