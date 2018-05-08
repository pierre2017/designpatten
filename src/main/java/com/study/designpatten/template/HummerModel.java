package com.study.designpatten.template;

/**
 * Created by pierre on 2018/5/8.
 * 悍马车模型
 */
public abstract class HummerModel {
    //模型启动方法
    protected abstract void start();

    //模型停止方法
    protected abstract void stop();

    //鸣笛
    protected abstract void alarm();

    //发动机轰轰
    protected abstract void engineBoom();

    //那模型应该会跑吧， 别管是人推的， 还是电力驱动， 总之要会跑
    final public void run() {
        //先发动汽车
        this.start();
        //引擎开始轰鸣
        this.engineBoom();
        //要让它叫的就是就叫， 喇嘛不想让它响就不响
        if (this.isAlarm()) {
            this.alarm();
        }
        //到达目的地，停车
        this.stop();
    }

    //钩子方法，默认喇叭是会响的
    protected boolean isAlarm() {
        return true;
    }
}
