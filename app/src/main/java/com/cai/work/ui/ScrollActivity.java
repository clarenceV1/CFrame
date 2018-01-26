package com.cai.work.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.cai.work.R;
import com.cai.work.bean.ScrollData;
import com.cai.work.ui.adapter.ScrollAdapter;
import com.cai.work.widget.OnRefreshListener;
import com.cai.work.widget.RefreshListView;

import java.util.ArrayList;

/**
 * Created by clarence on 2018/1/22.
 */

public class ScrollActivity extends Activity {

    private RefreshListView mListView;
    private ScrollAdapter adapter;
    private ArrayList<ScrollData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);

        init();
    }

    private void init() {
        mListView = (RefreshListView) findViewById(R.id.listview);
        data = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            ScrollData scrollData = new ScrollData();
            scrollData.setName("这是第" + i + "个数据");
            data.add(scrollData);
        }
        //初始化adapter
        adapter = new ScrollAdapter(this, data);
        mListView.setAdapter(adapter);
        mListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void downPullRefresh() {
                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        SystemClock.sleep(2800);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                        adapter.notifyDataSetChanged();
                        mListView.hideHeaderView();
                    }
                }.execute(new Void[]{});
            }
        });
    }
}
