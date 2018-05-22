package com.study.designpatten.observer.nomal;

/**
 * Created by pierre on 2018/5/22.
 */
public class Hanfeizi implements IHanfeizi {
    //韩非子是否在吃饭，作为监控的标准
    private boolean isHavingBreakfast = false;
    //韩非子是否在娱乐
    private boolean isHavingFun = false;

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子：开始吃饭...");
        this.isHavingBreakfast = true;
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子：开始娱乐...");
        this.isHavingFun = true;
    }

    public boolean isHavingBreakfast() {
        return isHavingBreakfast;
    }

    public void setHavingBreakfast(boolean havingBreakfast) {
        isHavingBreakfast = havingBreakfast;
    }

    public boolean isHavingFun() {
        return isHavingFun;
    }

    public void setHavingFun(boolean havingFun) {
        isHavingFun = havingFun;
    }
}
