package com.study.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算
 */
public class ForkJoinTest extends RecursiveTask<Integer> {

    private final int threadhold = 5;
    private int first;
    private int last;


    public ForkJoinTest(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first < threadhold) {
            for (int i = first; i < last; i++) {
                // 任务足够小则直接计算
                result += i;
            }
        } else {
            // 拆分成小任务
            int middle = first + (last - first) / 2;
            ForkJoinTest leftTask = new ForkJoinTest(first, middle);
            ForkJoinTest rightTask = new ForkJoinTest(middle + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinTest forkJoinTest = new ForkJoinTest(1, 10000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future future = forkJoinPool.submit(forkJoinTest);
        System.out.println(future.get());
    }
}
