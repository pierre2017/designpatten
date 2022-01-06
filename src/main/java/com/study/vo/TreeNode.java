package com.study.vo;

public class TreeNode<T> {

    public T value;

    public TreeNode<T> left;

    public TreeNode<T> right;

    public T getValue() {
        return value;
    }

    public TreeNode() {

    }

    public TreeNode(T value) {
        this.value = value;
    }
}
