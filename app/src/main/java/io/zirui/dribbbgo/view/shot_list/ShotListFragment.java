package io.zirui.dribbbgo.view.shot_list;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.zirui.dribbbgo.R;
import io.zirui.dribbbgo.dribbble.Dribbble;
import io.zirui.dribbbgo.model.Shot;
import io.zirui.dribbbgo.view.base.SpaceItemDecoration;

public class ShotListFragment extends Fragment{

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ShotListAdapter adapter;

    public static ShotListFragment newInstance(){
        return new ShotListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(getResources()
                .getDimensionPixelSize((R.dimen.spacing_small))));
        final Handler handler = new Handler();
        adapter = new ShotListAdapter(new ArrayList<Shot>(), new ShotListAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
//                AsyncTaskCompat.executeParallel(
//                        new LoadShotTask(adapter.getDataCount() / Dribbble.COUNT_PER_PAGE + 1)
//                );
                LoadShotTask loadShotTask = new LoadShotTask(adapter.getDataCount() / Dribbble.COUNT_PER_PAGE + 1);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    loadShotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    loadShotTask.execute();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private class LoadShotTask extends AsyncTask<Void, Void, List<Shot>> {

        int page;

        public LoadShotTask(int page){
            this.page = page;
        }

        @Override
        protected List<Shot> doInBackground(Void... voids) {
            try{
                return Dribbble.getShots(page);
            }catch (IOException | JsonSyntaxException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Shot> shots) {
            if (shots != null){
                adapter.append(shots);
                adapter.setShowLoading(shots.size() == Dribbble.COUNT_PER_PAGE);
            }else{
                Snackbar.make(getView(), "Error", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
