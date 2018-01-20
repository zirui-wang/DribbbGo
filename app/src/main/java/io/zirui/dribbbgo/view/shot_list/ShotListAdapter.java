package io.zirui.dribbbgo.view.shot_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.zirui.dribbbgo.R;
import io.zirui.dribbbgo.model.Shot;
import io.zirui.dribbbgo.utils.ModelUtils;
import io.zirui.dribbbgo.view.shot_detail.ShotActivity;
import io.zirui.dribbbgo.view.shot_detail.ShotFragment;

public class ShotListAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_SHOT = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private List<Shot> data;
    private LoadMoreListener loadMoreListener;
    private boolean showLoading;

    public ShotListAdapter(List<Shot> data, @NonNull LoadMoreListener loadMoreListener){
        this.data = data;
        this.loadMoreListener = loadMoreListener;
        this.showLoading = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SHOT){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_shot, parent, false);
            return new ShotViewHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_loading, parent, false);
            return new RecyclerView.ViewHolder(view){};
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShotViewHolder){
            final Shot shot = data.get(position);

            ShotViewHolder shotViewHolder = (ShotViewHolder) holder;
            shotViewHolder.viewCount.setText(String.valueOf(shot.views_count));
            shotViewHolder.likeCount.setText(String.valueOf(shot.likes_count));
            shotViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));

            // play gif automatically
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(shot.getImageUrl()))
                    .setAutoPlayAnimations(true)
                    .build();
            shotViewHolder.image.setController(controller);

            shotViewHolder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, ShotActivity.class);
                    intent.putExtra(ShotFragment.KEY_SHOT,
                            ModelUtils.toString(shot, new TypeToken<Shot>(){}));
                    intent.putExtra(ShotActivity.KEY_SHOT_TITLE, shot.title);
                    context.startActivity(intent);
                }
            });
        }else{
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return showLoading ? data.size() + 1 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position < data.size()
                ? VIEW_TYPE_SHOT
                : VIEW_TYPE_LOADING;
    }

    public int getDataCount(){
        return data.size();
    }

    public void append(@NonNull List<Shot> moreShot){
        data.addAll(moreShot);
        notifyDataSetChanged();
    }

    public void setShowLoading(boolean showLoading){
        this.showLoading = showLoading;
        notifyDataSetChanged();
    }

    public interface LoadMoreListener{
        void onLoadMore();
    }
}
