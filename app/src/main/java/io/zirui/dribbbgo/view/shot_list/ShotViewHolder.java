package io.zirui.dribbbgo.view.shot_list;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import io.zirui.dribbbgo.R;
import io.zirui.dribbbgo.view.base.BaseViewHolder;

public class ShotViewHolder extends BaseViewHolder {

    @BindView(R.id.shot_like_count) public TextView likeCount;
    @BindView(R.id.shot_bucket_count) public TextView bucketCount;
    @BindView(R.id.shot_view_count) public TextView viewCount;
    @BindView(R.id.shot_image) public SimpleDraweeView image;
    @BindView(R.id.shot_clickable_cover) public View cover;

    public ShotViewHolder(View itemView) {
        super(itemView);
    }
}
