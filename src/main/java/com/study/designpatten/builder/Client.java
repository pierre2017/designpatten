package com.study.designpatten.builder;

import java.util.ArrayList;

/**
 * Created by pierre on 2018/5/8.
 */
public class Client {
    public static void main(String[] args) {
        ArrayList<String> sequence = new ArrayList<String>();
        sequence.add("engine boom");
        sequence.add("start");
        sequence.add("stop");

        BenzBuilder benzBuilder = new BenzBuilder();
        benzBuilder.setSequence(sequence);
        CarModel benzModel = benzBuilder.getCarModel();
        benzModel.run();

        System.out.println("=========");

        BMWBuilder bmwBuilder = new BMWBuilder();
        bmwBuilder.setSequence(sequence);
        CarModel bmwModel = bmwBuilder.getCarModel();
        bmwModel.run();

        System.out.println("*****************************");

        //制造多个模型
        Director director = new Director();
        for (int i = 0; i < 10; i++) {
            director.getABenzModel().run();
            System.out.println("=====");
            director.getABmwModel().run();
            System.out.println("=====");
            director.getBBenzModel().run();
            System.out.println("=====");
            director.getBBmwModel().run();
            System.out.println("=====");
        }
    }
}
