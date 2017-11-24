package com.example.yshe.carcontrol;

import android.app.Application;

/**
 * Created by yshe on 2017/1/5.
 */

public class Data extends Application{
    private int movetype;

    public int getType(){
        return this.movetype;
    }
    public void setType(int t){
        this.movetype=t;
    }
    @Override
    public void onCreate(){
        movetype=0;
        super.onCreate();
    }
}
