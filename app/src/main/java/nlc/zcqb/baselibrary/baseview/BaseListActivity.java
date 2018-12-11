package nlc.zcqb.baselibrary.baseview;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ncl.zcqb.app.R;
import nlc.zcqb.baselibrary.presenter.BasePresenter;


/**
 * Created by lvqiu on 2018/10/14.
 */

public abstract class BaseListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoadingMore=false;
    private LinearLayoutManager layoutManager;
    private BaseTypeAdapter adapter;
    private boolean ispause=true;
    private RecyclerView.OnScrollListener scrollListener;
    private boolean isScrolling=false;
    private List<BasePresenter> basePresenters=new ArrayList<>();
    private int WAITINGTIME=3000;
    public Handler mHandler=new Handler();

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler=null;
        for (BasePresenter basePresenter: basePresenters) {
            basePresenter.detach();
        }
        basePresenters.clear();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public final void InitMenu() {

        swipeRefreshLayout= (SwipeRefreshLayout)  findViewById(R.id.swiperefreshlayout);
        recyclerView=(RecyclerView)  findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollListener= new  RecycleScrollListener(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setColorSchemeResources(R.color.md_pink_650);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                asyRefreshData();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopLoadingAnimation();
                    }
                },WAITINGTIME);
            }
        });
    }

    public abstract  void  initTitle();

    public BaseTypeAdapter getAdapter() {
        return adapter;
    }

    public void addPresenter(BasePresenter basePresenter) {
        if (basePresenter!=null)
            basePresenters.add(basePresenter);
    }

    public void setAdapter(BaseTypeAdapter adapter){
        this.adapter=adapter;
        recyclerView.setAdapter(adapter);
    }
    public void startLoadingAnimation(){
        swipeRefreshLayout.setRefreshing(true);
    }
    public void stopLoadingAnimation(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public abstract void asyRefreshData();

    public abstract void asyLoadingMoreData();

    public class RecycleScrollListener extends   RecyclerView.OnScrollListener  {
        private   RecyclerView.LayoutManager layoutManager=null;
        public RecycleScrollListener(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }
        /**
         * Callback method to be invoked when RecyclerView's scroll state changes.
         *
         * @param recyclerView The RecyclerView whose scroll state has changed.
         * @param newState
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!ispause)
                if (newState == RecyclerView.SCROLL_STATE_IDLE ) {
                    isScrolling=false;
                    Glide.with(BaseListActivity.this).resumeRequests();
                } else {
                    isScrolling=true;
                    Glide.with(BaseListActivity.this).pauseRequests();
                }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                if(isLoadingMore){
                    Log.e("onScrolled","ignore manually update!");
                } else if (isScrolling){
                    asyLoadingMoreData();
                    isLoadingMore = true;
                }
            }
        }
    }
}
