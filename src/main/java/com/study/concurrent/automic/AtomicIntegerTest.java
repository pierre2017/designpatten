package com.study.concurrent.automic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    private AtomicInteger cnt = new AtomicInteger();

    public void add() {
        cnt.incrementAndGet();
    }


    public int get() {
        return cnt.get();
    }


    public static void main(String[] args) throws InterruptedException {
        final int thread = 1000;
        final AtomicIntegerTest t = new AtomicIntegerTest();
        final CountDownLatch countDownLatch = new CountDownLatch(thread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < thread; i++)
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    t.add();
                    countDownLatch.countDown();
                }
            });
        countDownLatch.await();
        System.out.println(t.get());
    }
}
