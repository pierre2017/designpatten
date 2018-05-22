package com.study.designpatten.observer.design;

/**
 * Created by pierre on 2018/5/22.
 */
public class Lisi implements Observer {
    @Override
    public void update(String content) {
        System.out.println("李斯： 观察到韩非子活动， 开始向老板汇报了...");
        this.reportToQinShiHuang(content);
        System.out.println("李斯： 汇报完毕...\n");
    }

    //汇报给秦始皇
    private void reportToQinShiHuang(String reportContext) {
        System.out.println("李斯： 报告， 秦老板！ 韩非子有活动了-->" + reportContext);
    }
}
