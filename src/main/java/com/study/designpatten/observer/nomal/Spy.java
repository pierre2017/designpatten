package com.study.designpatten.observer.nomal;

/**
 * Created by pierre on 2018/5/22.
 */
public class Spy extends Thread {

    private Hanfeizi hanFeiZi;
    private Lisi liSi;
    private String type;

    //通过构造函数传递参数， 我要监控的是谁， 谁来监控， 要监控什么
    public Spy(Hanfeizi _hanFeiZi, Lisi _liSi, String _type) {
        this.hanFeiZi = _hanFeiZi;
        this.liSi = _liSi;
        this.type = _type;
    }

    @Override
    public void run() {
        while (true) {
            if ("breakfast".equals(this.type)) {//监控是否在吃早饭
                this.liSi.update("韩非子在吃饭");
                //充值监控状态，以便继续监控
                this.hanFeiZi.setHavingBreakfast(false);
            } else {//监控是否在娱乐
                this.liSi.update("韩非子在娱乐");
                this.hanFeiZi.setHavingFun(false);
            }
        }
    }
}
