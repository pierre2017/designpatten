package com.study.designpatten.nullobject.nomal;

import org.junit.Test;

public class Client {

    /**
     * 普通调用
     */
    @Test
    public void nomal() {
        BookFactory bookFactory = new BookFactory();
        ConcreteBook book = bookFactory.getBook(1);
        book.show();
    }

    /**
     * 空指针调用
     */
    @Test
    public void nullPointer() {
        BookFactory bookFactory = new BookFactory();
        ConcreteBook book = bookFactory.getBook(-1);
        book.show();
    }

    /**
     * 非空指针调用
     */
    @Test
    public void noNullPointer() {
        BookFactory bookFactory = new BookFactory();
        ConcreteBook book = bookFactory.getBook(-1);
        //判断book对象是否为null。
        if (book == null) {
            System.out.println("book对象为 null。");
        } else {
            book.show();
        }
    }


}