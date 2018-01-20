package io.zirui.dribbbgo.view.shot_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.zirui.dribbbgo.R;


public class ImageViewHolder extends RecyclerView.ViewHolder{

    SimpleDraweeView image;

    public ImageViewHolder(View itemView) {
        super(itemView);
        image = (SimpleDraweeView) itemView;
    }
}
