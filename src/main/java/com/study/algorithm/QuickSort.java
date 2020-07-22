package com.study.algorithm;


import java.util.Arrays;

/**
 * @author duanshichao
 * @date 2020/7/21
 * @Desc
 */
public class QuickSort {


    /**
     * 快速排序
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     l -- 数组的左边界(例如，从起始位置开始排序，则l=0)
     *     r -- 数组的右边界(例如，排序截至到数组末尾，则r=a.length-1)
     */
    public static void quicksort(int[] a,int l,int r){
       if(l<=r){
           int i,j,key;
           i=l;
           j= r;
           key=a[i];
           while (i<j){
             while (i<j&&a[j]>=key){
                 j--;// 从右向左找第一个小于key的数
             }
             if(i<j) {
                 a[i] = a[j];
             }
             while(i<j&&a[i]<=key){
                 i++; //从左到又查找大于key的数
             }
             if (i<j){
                 a[j]=a[i];
             }
           }
           a[i]=key;
           quicksort(a,l,i-1);
           quicksort(a,i+1,r);
       }
    }


    public static void main(String[] args) {
        int[] a={10,30,20,40,60,50,90,60,70,80};
        System.out.println("before sort:"+Arrays.toString(a));
        quicksort(a,0,a.length-1);
        System.out.println("after sort:"+Arrays.toString(a));
    }
}
