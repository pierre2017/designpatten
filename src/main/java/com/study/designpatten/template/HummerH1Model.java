package com.study.designpatten.template;

/**
 * Created by pierre on 2018/5/8.
 * 悍马H1模型
 */
public class HummerH1Model extends HummerModel {
    private boolean alarmFlag = true; //要响喇叭

    @Override
    protected void start() {
        System.out.println("悍马H1发动...");
    }

    @Override
    protected void stop() {
        System.out.println("悍马H1停车...");
    }

    @Override
    protected void alarm() {
        System.out.println("悍马H1鸣笛...");
    }

    @Override
    protected void engineBoom() {
        System.out.println("悍马H1引擎声音是这样的...");
    }

    protected boolean isAlarm() {
        return this.alarmFlag;
    }

    //要不要响喇叭， 是由客户来决定的
    public void setAlarm(boolean isAlarm) {
        this.alarmFlag = isAlarm;
    }
}
