package nlc.zcqb.app.daichaoview.fourth.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.first.bean.RecommendBean;
import nlc.zcqb.app.daichaoview.fourth.bean.QueryBean;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.daichaoview.second.adapter.SecondAdapter;
import nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseListActivity;

import static nlc.zcqb.app.daichaoview.second.fragment.Secondfragment.PINGTAIMORETYPE;
import static nlc.zcqb.app.daichaoview.second.fragment.Secondfragment.PINGTAITYPE;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class ShouCangActivity extends BaseListActivity implements View.OnClickListener,CommonView {
    private TitleBarViewHolder viewHolder;
    private QueryBean queryBean=new QueryBean(1, MyApplication.mUser.getUser_id());
    private CommonPresenter<RecommendBean> commonPresenter;


    public int getLayoutId(){
        return R.layout.fragment_third;
    }

    public void initTitle() {
        viewHolder =new TitleBarViewHolder(this);

        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titlename.setText(R.string.favorate);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusColor(R.color.mosttransparentgray);
        }
    }


    @Override
    public void InitOthers() {
        initTitle();
        commonPresenter=new CommonPresenter<RecommendBean>(this){};
        setAdapter(new SecondAdapter(this));
        commonPresenter.getCommonData(PINGTAITYPE,queryBean, DC.chaxunshoucang,true);
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        queryBean.setPage(1);
        commonPresenter.getCommonData(PINGTAITYPE,queryBean, DC.chaxunshoucang,true);
    }

    @Override
    public void asyLoadingMoreData() {
        commonPresenter.getCommonData(PINGTAIMORETYPE,queryBean, DC.chaxunshoucang,true);
    }

    @Override
    public void success(Object o, int type) {
        if (type==PINGTAITYPE){
            queryBean.setPage(2);
            getAdapter().updateData(o);
        }else if (type==PINGTAIMORETYPE){
            ArrayList<RecommendBean> temp= (ArrayList<RecommendBean>) o;
            if (temp!=null && temp.size()>0){
                queryBean.page++;
            }
            getAdapter().loadMoreData(o);
        }
        stopLoadingAnimation();
    }
    @Override
    public void failure(Object o, int type) {
        stopLoadingAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action:
                ARouter.jumpIn(this,new Bundle(), DaiKuanDetailActivity.class);
                break;
        }
    }
}
