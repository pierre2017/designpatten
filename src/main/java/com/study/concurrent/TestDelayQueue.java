package com.study.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        DelayQueue<Task> delayqueue = new DelayQueue<Task>();
        delayqueue.put(new Task(now + 1000));
        delayqueue.put(new Task(now + 3000));
        delayqueue.put(new Task(now + 10000));

        for (int i = 0; i < 3; i++) {
            System.out.println(delayqueue.take());
        }

    }

    static class Task implements Delayed {

        long time;

        public Task(long time) {
            this.time = time;
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
                return -1;
            else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
                return 1;
            else
                return 0;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return "" + time;
        }
    }

}
