package com.study.designpatten.singleton;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pierre on 2018/5/3.
 * 测试普通 单例并发情况
 * 运行main方法可能出现多个实例
 */
public class Client {

    public static void main(String[] args) throws Exception {
        int num = 100;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("Inside Barrier");
            }
        });
        final Set<String> set = Collections.synchronizedSet(new HashSet<String>());
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < num; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        cyclicBarrier.await();//阻塞等待所有线程创建完毕，然后同时执行获取实例的操作
                        Singleton singleton = Singleton.getInstance();
                        set.add(singleton.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Thread.sleep(1000);
        System.out.println("------并发情况下我们取到的实例------");
        for (String instance : set) {
            System.out.println(instance);
        }
        executorService.shutdown();

    }
}
