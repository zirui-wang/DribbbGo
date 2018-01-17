package io.zirui.dribbbgo.view.shot_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.zirui.dribbbgo.R;
import io.zirui.dribbbgo.model.Shot;


class ShotAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_SHOT_IMAGE = 0;
    private static final int VIEW_TYPE_SHOT_INFO = 1;

    private final Shot shot;

    public ShotAdapter(@NonNull Shot shot) {
        this.shot = shot;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            switch (viewType){
                case VIEW_TYPE_SHOT_IMAGE:
                    view = LayoutInflater.from(parent.getContext())
                                         .inflate(R.layout.shot_item_image, parent, false);
                    return new ImageViewHolder(view);
                case VIEW_TYPE_SHOT_INFO:
                    view = LayoutInflater.from(parent.getContext())
                                         .inflate(R.layout.shot_item_info, parent, false);
                    return new InfoViewHolder(view);
                default:
                    return null;
            }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case VIEW_TYPE_SHOT_IMAGE:
                break;
            case VIEW_TYPE_SHOT_INFO:
                InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
                infoViewHolder.title.setText(shot.title);
                infoViewHolder.authorName.setText(shot.user.name);
                infoViewHolder.description.setText(shot.description);

                infoViewHolder.likeCount.setText(shot.likes_count);
                infoViewHolder.bucketCount.setText(shot.buckets_count);
                infoViewHolder.viewCount.setText(shot.views_count);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return VIEW_TYPE_SHOT_IMAGE;
        }else{
            return VIEW_TYPE_SHOT_INFO;
        }
    }
}
