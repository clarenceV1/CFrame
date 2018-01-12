package com.cai.work.bean;

import android.util.Log;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.annotation.aspect.CostTime;

/**
 * Created by clarence on 2018/1/10.
 */
@InstanceFactory
public class Animal {
    private static final String TAG = "Animal";

    public void fly() {
        Log.e(TAG, this.toString() + "#fly");
    }

    public Animal() {
    }
}
