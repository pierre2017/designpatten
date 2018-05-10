package com.study.designpatten.prorotype.design;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * Created by pierre on 2018/5/10.
 */
public class Thing implements Cloneable {
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected Thing clone() {
        Thing thing = null;
        try {
            thing = (Thing) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thing;
    }

    public void setValue(String value) {
        this.arrayList.add(value);
    }

    public void getValue() {
        System.out.println(JSON.toJSONString(this.arrayList));
    }


    public static void main(String[] args) {
        Thing thing = new Thing();
        thing.setValue("张三");

        Thing clone = thing.clone();
        clone.setValue("李四");

        //浅拷贝问题  得到的值为["张三","李四"]
        /*
        *   内部的数组和引用对象才不拷贝，
        *   其他的原始类型比如int、 long、 char等都会被拷贝， 但是对于String类型， Java就希望你把它
        *   认为是基本类型， 它是没有clone方法的， 处理机制也比较特殊， 通过字符串池（stringpool）
        */
        thing.getValue();
    }
}
