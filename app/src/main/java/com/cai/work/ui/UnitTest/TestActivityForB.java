package com.cai.work.ui.UnitTest;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.cai.work.R;

public class TestActivityForB extends FragmentActivity {

    String lifecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_test);
        lifecycle = "onCreate";
    }

    public String getName() {
        return "TestActivityForB";
    }

    public void jump(View view) {
        Toast.makeText(this, "Hello UT!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, TestActivityForA.class));
    }

    public void showDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Hello UT！")
                .setTitle("提示")
                .create();
        alertDialog.show();
    }

    public String getLifecycleState(){
        return lifecycle;
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycle = "onStart";
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycle = "onResume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycle = "onPause";
    }
    @Override
    protected void onStop() {
        super.onStop();
        lifecycle = "onStop";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lifecycle = "onRestart";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycle = "onDestroy";
    }
}
