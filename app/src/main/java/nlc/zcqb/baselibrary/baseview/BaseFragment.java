package nlc.zcqb.baselibrary.baseview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bumptech.glide.Glide;
import nlc.zcqb.baselibrary.event.ClickEvent;
import nlc.zcqb.baselibrary.presenter.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxl on 2016/9/21.
 */
abstract public class BaseFragment extends Fragment {
    private List<BasePresenter> basePresenters=new ArrayList<>();
    private String pageName;

    public BaseFragment() {
        super();
        pageName = getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    abstract public void onEvent(ClickEvent event);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.get(getActivity()).clearMemory();
        Glide.with(this).resumeRequests();
        if (getUserVisibleHint()) {
            onVisivilityChangedToUser(true, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Glide.get(getActivity()).clearMemory();
        Glide.with(this).pauseRequests();
        if (getUserVisibleHint()) {
            onVisivilityChangedToUser(false, false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    public void addPresenter(BasePresenter basePresenter) {
        if (basePresenter!=null)
            basePresenters.add(basePresenter);
    }
    @Override
    public void onDestroy() {
        for (BasePresenter basePresenter: basePresenters) {
            basePresenter.detach();
        }
        if (basePresenters!=null){
            for (BasePresenter b: basePresenters) {
                if (b!=null){
                    b.detach();
                }
            }
        }
        basePresenters.clear();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            onVisivilityChangedToUser(isVisibleToUser, false);
        }
    }

    public void onVisivilityChangedToUser(boolean isVisivility, boolean isHappenedSetUserVisibleHintMethod) {
        if (isVisivility) {
            if (null != pageName) {

                Log.i("UmengPageTrack", pageName + " - display - "+(isHappenedSetUserVisibleHintMethod?"setUserVisibleHint":"onResume"));
            }
        } else {
            if (null != pageName) {

                Log.i("UmengPageTrack", pageName + " - display - "+(isHappenedSetUserVisibleHintMethod?"setUserVisibleHint":"onResume"));
            }
        }
    }

}
