package com.cai.work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cai.annotation.apt.Router;
import com.cai.annotation.aspect.CostTime;
import com.cai.work.utils.InstanceUtil;

@Router("woderouter")
public class MainActivity extends AppCompatActivity {

    @Override
    @CostTime
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animal animal = InstanceUtil.getInstance(Animal.class);
        animal.fly();
    }
}
