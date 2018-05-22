package com.study.designpatten.strategy;

/**
 * Created by pierre on 2018/5/22.
 * 吴国太开绿灯
 */
public class GiveGreenLight implements Istrategy {
    @Override
    public void operate() {
        System.out.println("求吴国太开绿灯,放行！");
    }
}
