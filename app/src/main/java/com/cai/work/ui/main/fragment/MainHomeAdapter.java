package com.cai.work.ui.main.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.work.R;

public class MainHomeAdapter extends RecyclerView.Adapter<MainHomeAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){

        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookname;

        public ViewHolder(View view) {
            super(view);
//            bookImage = (ImageView) view.findViewById(R.id.book_iamge);
//            bookname = (TextView) view.findViewById(R.id.book_name);
        }
    }
}
