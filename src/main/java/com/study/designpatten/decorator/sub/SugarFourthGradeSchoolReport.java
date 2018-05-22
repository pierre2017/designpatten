package com.study.designpatten.decorator.sub;

/**
 * Created by pierre on 2018/5/22.
 */
public class SugarFourthGradeSchoolReport extends FourthGradeSchoolReport {

    //首先定义美化的方法，先给老爸说学校最高成绩
    private void reportHighScore() {
        System.out.println("这次语文考试最高是75分，数学最高78分");
    }

    //在老爸看完成绩单后，再汇报学校的排名情况
    private void reportSort() {
        System.out.println("我的排名是38名");
    }

    @Override
    public void report() {
        this.reportHighScore();//先说最高成绩
        super.report();        //然后老爸开始看成绩单
        this.reportSort();     //然后告诉老爸在学校的排名情况
    }
}
