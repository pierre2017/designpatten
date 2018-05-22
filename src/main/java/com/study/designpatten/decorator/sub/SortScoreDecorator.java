package com.study.designpatten.decorator.sub;

/**
 * Created by pierre on 2018/5/22.
 */
public class SortScoreDecorator extends Decorator {


    public SortScoreDecorator(SchoolReport sr) {
        super(sr);
    }

    //告诉老爸学校的排名情况
    private void reportSort() {
        System.out.println("我是排名第38名...");
    }

    //老爸看完成绩单后再告诉他， 加强作用
    @Override
    public void report() {
        this.reportSort();
        super.report();
    }


}
