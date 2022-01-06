package com.study.algorithm.linkedlist;

import com.study.vo.ListNode;
import com.study.vo.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedListTest {


    public static void main(String[] args) {
        ListNode<Integer> listNode = new ListNode<>(0);
        ListNode<Integer> tmp = listNode;
        for (int i = 0; i < 10; i++) {
            ListNode<Integer> listNode1 = new ListNode<>(i + 1);
            tmp.next = listNode1;
            tmp = listNode1;
        }
        traverse(listNode);
    }

    static <T> void traverse(ListNode<T> head) {
        if (head == null) {
            return;
        }
        traverse(head.next);
        // 后序位置
        System.out.println(head.value);
    }


    // 输入一棵二叉树的根节点，层序遍历这棵二叉树
    static void levelTraverse(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        // 从上到下遍历二叉树的每一层
        while (!q.isEmpty()) {
            int sz = q.size();
            // 从左到右遍历每一层的每个节点
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                // 将下一层节点放入队列
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
        }
    }

}
