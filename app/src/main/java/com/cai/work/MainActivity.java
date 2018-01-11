package com.cai.work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cai.annotation.Router;

@Router("woderouter")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animal animal = new Animal();
        animal.fly();
    }
}
