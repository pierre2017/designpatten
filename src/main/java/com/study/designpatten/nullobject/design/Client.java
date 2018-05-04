package com.study.designpatten.nullobject.design;

import org.junit.Test;

public class Client {
    @Test
    public void nullObject() {
        BookFactory bookFactory = new BookFactory();
        Book book = bookFactory.getBook(-1);
        book.show();
    }
}
