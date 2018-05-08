package com.study.designpatten.builder;

import java.util.ArrayList;

/**
 * Created by pierre on 2018/5/8.
 */
public class Director {
    private ArrayList<String> sequence = new ArrayList<String>();

    private BenzBuilder benzBuilder = new BenzBuilder();

    private BMWBuilder bmwBuilder = new BMWBuilder();

    public BenzModel getABenzModel() {
        this.sequence.clear();
        this.sequence.add("start");
        this.sequence.add("stop");
        benzBuilder.setSequence(sequence);
        return benzBuilder.getCarModel();
    }

    public BenzModel getBBenzModel() {
        this.sequence.clear();
        this.sequence.add("start");
        this.sequence.add("engine boom");
        this.sequence.add("stop");
        benzBuilder.setSequence(sequence);
        return benzBuilder.getCarModel();
    }

    public BMWModel getABmwModel() {
        this.sequence.clear();
        this.sequence.add("start");
        this.sequence.add("engine boom");
        bmwBuilder.setSequence(this.sequence);
        return bmwBuilder.getCarModel();
    }

    public BMWModel getBBmwModel() {
        this.sequence.clear();
        this.sequence.add("start");
        this.sequence.add("engine boom");
        this.sequence.add("stop");
        bmwBuilder.setSequence(this.sequence);
        return bmwBuilder.getCarModel();
    }

}
