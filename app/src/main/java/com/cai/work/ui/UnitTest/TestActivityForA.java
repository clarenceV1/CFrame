package com.cai.work.ui.UnitTest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cai.work.R;

public class TestActivityForA extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_test_a);
    }

    public String getName() {
        return "TestActivityForA";
    }
}
