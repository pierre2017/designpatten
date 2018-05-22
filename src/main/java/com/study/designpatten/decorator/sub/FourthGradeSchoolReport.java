package com.study.designpatten.decorator.sub;

/**
 * Created by pierre on 2018/5/22.
 * 四年级成绩 单
 */
public class FourthGradeSchoolReport extends SchoolReport {
    //我的成绩单
    @Override
    public void report() {
        //成绩单的格式是这个样子的
        System.out.println("尊敬的XXX家长：");
        System.out.println("....");
        System.out.println("语文：62,数学65,体育89,自然23");
        System.out.println("....");
        System.out.println("            家长签名：               ");
    }

    @Override
    public void sign(String name) {
        System.out.println("家长签名为：" + name);
    }
}
