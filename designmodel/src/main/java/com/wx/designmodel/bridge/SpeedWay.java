package com.wx.designmodel.bridge;

/**
 * Created by Administrator on 2017/7/18.
 */
public class SpeedWay extends AbstractRoad {
    @Override
    void run() {
        car.run();
        System.out.println("speedWay");
    }
}
