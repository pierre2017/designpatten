package com.study.designpatten.decorator.sub;

/**
 * Created by pierre on 2018/5/22.
 */
public class Decorator extends SchoolReport {

    //首先我要知道是哪个成绩单
    private SchoolReport sr;

    //构造函数，传递成绩单
    public Decorator(SchoolReport sr) {
        this.sr = sr;
    }


    @Override
    public void report() {
        this.sr.report();
    }

    @Override
    public void sign(String name) {
        this.sr.sign(name);
    }
}
