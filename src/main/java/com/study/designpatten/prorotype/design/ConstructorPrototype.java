package com.study.designpatten.prorotype.design;

/**
 * Created by pierre on 2018/5/10.
 * Object类的
 * clone方法的原理是从内存中（具体地说就是堆内存） 以二进制流的方式进行拷贝， 重新分配
 * 一个内存块， 那构造函数没有被执行也是非常正常的了。
 */
public class ConstructorPrototype {
    public static void main(String[] args) {
        Mail mail = new Mail();

        Mail clone = mail.clone();
    }
}
