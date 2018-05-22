package com.study.designpatten.decorator.sub;

import org.junit.Test;

/**
 * Created by pierre on 2018/5/22.
 */
public class Father {

    @Test
    public void signNameOne() {
        SchoolReport schoolReport = new SugarFourthGradeSchoolReport();

        schoolReport.report();

        schoolReport.sign("张三");
    }

    @Test
    public void singNameTwo() {
        //把成绩单拿过来
        SchoolReport sr;
        //原装的成绩单
        sr = new FourthGradeSchoolReport();
        //加了最高分说明的成绩单
        sr = new HighScoreDecorator(sr);
        //又加了成绩排名的说明
        sr = new SortScoreDecorator(sr);
        //看成绩单
        sr.report();
        //然后老爸一看， 很开心， 就签名了
        sr.sign("老三"); //我叫小三， 老爸当然叫老三
    }
}
