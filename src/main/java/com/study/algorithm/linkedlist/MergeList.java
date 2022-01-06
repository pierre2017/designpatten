package com.study.algorithm.linkedlist;

import com.study.vo.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并列表
 */
public class MergeList {

    /**
     * 合并两个  有序链表
     *
     * @param l1 ListNode
     * @param l2 ListNode
     */
    public ListNode<Integer> mergeTwoList(ListNode<Integer> l1, ListNode<Integer> l2) {
        // 虚拟头结点
        ListNode<Integer> dummy = new ListNode<Integer>(-1), p = dummy;
        ListNode<Integer> p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {
            // 比较 p1 和 p2 两个指针
            // 将值较小的的节点接到 p 指针
            if (p1.value > p2.value) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p1.next;
            }
            // p 指针不断前进
            p = p.next;
        }
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }
        return dummy.next;
    }

    /**
     * 合并  K  个列表
     */
    public ListNode<Integer> mergeKList(ListNode<Integer>[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        //虚拟头结点
        ListNode<Integer> dummy = new ListNode<>(-1);
        ListNode<Integer> p = dummy;
        //优先级队列  最小堆
        PriorityQueue<ListNode<Integer>> pq = new PriorityQueue<>(lists.length, Comparator.comparingInt(a -> a.value));
        // 将 k 个链表的头结点加入最小堆
        for (ListNode<Integer> head : lists) {
            if (head != null)
                pq.add(head);
        }
        while (!pq.isEmpty()) {
            // 获取最小节点，接到结果链表中
            ListNode<Integer> node = pq.poll();
            p.next = node;
            if (node.next != null) {
                pq.add(node.next);
            }
            // p 指针不断前进
            p = p.next;
        }
        return dummy.next;
    }
}
