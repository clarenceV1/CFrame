package com.cai.work.ui;

import android.app.Activity;
import android.os.Bundle;

import com.cai.work.R;
import com.cai.work.bean.ScrollData;
import com.cai.work.ui.adapter.ScrollAdapter;
import com.cai.work.widget.ListViewScroll;

import java.util.ArrayList;

/**
 * Created by clarence on 2018/1/22.
 */

public class ScrollActivity extends Activity {

    private ListViewScroll mListView;
    private ScrollAdapter adapter;
    private ArrayList<ScrollData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);

        init();
    }

    private void init() {
        mListView = (ListViewScroll) findViewById(R.id.listview);
        data = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            ScrollData scrollData = new ScrollData();
            scrollData.setName("这是第" + i + "个数据");
            data.add(scrollData);
        }
        //初始化adapter
        adapter = new ScrollAdapter(this, data);
        mListView.setAdapter(adapter);
    }
}
