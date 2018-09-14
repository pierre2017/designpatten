package com.study.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(4);

    private static class Producer extends Thread {
        @Override
        public void run() {
            try {
                queue.put("product");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("produce...");
        }
    }

    private static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                String take = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("consume ...");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            Producer p = new Producer();
            p.start();
        }

        for (int i = 0; i < 2; i++) {
            Consumer c = new Consumer();
            c.start();
        }

        for (int i = 0; i < 4; i++) {
            Producer p = new Producer();
            p.start();
        }
    }
}
