package com.cai.work.ui.nationcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cai.work.R;
import com.cai.work.bean.NationCodeModel;

import java.util.List;


public class NationCodeListAdapter extends BaseAdapter {
    Context mContext;
    List<NationCodeModel> mData;
    LayoutInflater inflater;

    public NationCodeListAdapter(Context ctx, List<NationCodeModel> data) {
        mContext = ctx;
        mData = data;
        inflater = LayoutInflater.from(mContext);
    }

    public void update(List<NationCodeModel> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public NationCodeModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView listItemView = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.nation_code_sort_item, null);
            listItemView = new ListItemView(convertView);
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        listItemView.tvName.setText(mData.get(position).getContry());
        listItemView.tvNationCode.setText("+" + mData.get(position).getCode());
        return convertView;
    }

    public class ListItemView {
        TextView tvName, tvNationCode;
        LinearLayout layout;

        public ListItemView(View convertView) {
            tvName = convertView.findViewById(R.id.tvName);
            tvNationCode = convertView.findViewById(R.id.tvNationCode);
            layout = convertView.findViewById(R.id.layout_container);
        }
    }

}
