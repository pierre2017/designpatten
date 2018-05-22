package com.study.designpatten.decorator.sub;

/**
 * Created by pierre on 2018/5/22.
 * 成绩单
 */
public abstract class SchoolReport {
    //成绩单 主要展示的就是你的成绩情况
    public abstract void report();

    //成绩单要家长签字
    public abstract void sign(String name);
}
