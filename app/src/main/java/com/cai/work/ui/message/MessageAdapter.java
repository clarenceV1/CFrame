package com.cai.work.ui.message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.cai.work.R;
import com.cai.work.bean.Message;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;

public class MessageAdapter extends BasePtrAdapter<Message, MessageAdapter.ViewHolder> {
    Context context;
    ILoadImage iLoadImage;

    public MessageAdapter(Context context, ILoadImage iLoadImage) {
        super();
        this.context = context;
        this.iLoadImage = iLoadImage;

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Message data, int position) {
        holder.titleTV.setText(data.getTitle());
        holder.contentTV.setText(data.getNickname());
        holder.timeTV.setText(data.getCreate_dt());
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(data.getAvatar())
                .build();

        imageParams.setImageView(holder.headIv);
        iLoadImage.loadImage(context, imageParams);
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.message_item);
        ViewHolder viewHolder = new ViewHolder(itemView, new BaseViewHold.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        return viewHolder;
    }

    class ViewHolder extends BasePtrViewHold {
        ImageView headIv;
        TextView titleTV;
        TextView timeTV;
        TextView contentTV;

        public ViewHolder(View itemView, BaseViewHold.OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            headIv = getView(R.id.msg_item_head_iv);
            titleTV = getView(R.id.msg_item_title_tv);
            timeTV = getView(R.id.msg_item_time_tv);
            contentTV = getView(R.id.msg_item_content_tv);
        }
    }
}
