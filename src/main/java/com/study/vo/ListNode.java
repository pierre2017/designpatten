package com.study.vo;

import java.io.Serializable;

/**
 * list node
 *
 * @param <T>
 */
public class ListNode<T> implements Serializable {

    public T value;

    public ListNode<T> next;

    public ListNode() {
    }


    public ListNode(T value) {
        this.value = value;
    }

}
