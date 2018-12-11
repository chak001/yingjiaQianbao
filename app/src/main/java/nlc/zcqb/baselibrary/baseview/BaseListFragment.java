package nlc.zcqb.baselibrary.baseview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ncl.zcqb.app.R;
import nlc.zcqb.app.event.StopLoadingEvent;
import nlc.zcqb.app.event.ClickEvent;
import nlc.zcqb.baselibrary.callback.UpdateView;
import nlc.zcqb.baselibrary.presenter.BasePresenter;

/**
 * Created by lvqiu on 2018/10/13.
 */

public abstract class BaseListFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoadingMore=false;
    private LinearLayoutManager layoutManager;
    private BaseTypeAdapter adapter;
    private boolean ispause=true;
    private RecyclerView.OnScrollListener scrollListener;
    private boolean isScrolling=false;

    private int WAITINGTIME=3000;
    public Handler mHandler=new Handler();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && recyclerView!=null){
            ispause=false;
            recyclerView.addOnScrollListener(scrollListener);
            Glide.with(getContext()).resumeRequests();
        }else  if (recyclerView!=null){
            recyclerView.removeOnScrollListener(scrollListener);
            ispause=true;
            Glide.with(getContext()).pauseRequests();
        }
    }


    @SuppressLint({"NewApi", "ValidFragment"})
    public BaseListFragment() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StopLoadingEvent event) {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public BaseListFragment(FragmentManager fragmentManager) {
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler=null;
        super.onDestroy();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(getContext() );
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollListener= new RecycleScrollListener(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);

        initViews(view);
        initOther();

        swipeRefreshLayout.setColorSchemeResources(R.color.md_pink_700);
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
        asyRefreshData();
        return view;
    }


    public int getLayoutId(){
        return R.layout.fragment_main;
    }

    /**
     * 初始化适配器，preseneter等等
     */
    public abstract void initOther();

    public abstract void initViews(View view);

    public BaseTypeAdapter getAdapter() {
        return adapter;
    }


    public void setAdapter(BaseTypeAdapter adapter){
        this.adapter=adapter;
        recyclerView.setAdapter(adapter);
    }
    public void updateDataByType(Object o,int type,int updatetype){
        adapter.TypeFactory(o,type,updatetype);
    }

    public void updateData(Object o){
        adapter.updateData(o);
    }

    public void loadMore(Object o){
        adapter.loadMoreData(o);
    }

    public void stopLoadingAnimation(){
        swipeRefreshLayout.setRefreshing(false);
    }
    public void startLoadingAnimation(){
        swipeRefreshLayout.setRefreshing(true);
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
            if (!ispause) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                    Glide.with(getContext()).resumeRequests();
                } else {
                    isScrolling = true;
                    Glide.with(getContext()).pauseRequests();
                }
            }
            if (myscrollListener!=null){
                myscrollListener.onScrollStateChanged(recyclerView,newState);
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
            if (myscrollListener!=null){
                myscrollListener.onScrolled(recyclerView,dx,dy);
            }
        }
    }

    private OnScrollListener myscrollListener;

    public void setScrollListener(OnScrollListener scrollListener) {
        this.myscrollListener = scrollListener;
    }

    public interface OnScrollListener{
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
        void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }
}
