package nlc.zcqb.app.daichaoview.fourth.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.first.bean.RecommendBean;
import nlc.zcqb.app.daichaoview.fourth.adapter.MessageAdapter;
import nlc.zcqb.app.daichaoview.fourth.bean.MessageBean;
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

public class MessageListActivity extends BaseListActivity  implements View.OnClickListener,CommonView{
    private TitleBarViewHolder viewHolder;
    private QueryBean queryBean=new QueryBean(1, MyApplication.mUser.getUser_id());
    private CommonPresenter<MessageBean> commonPresenter;
    public final static int MESSTYPE=27,MESSMORETYPE=28;


    public int getLayoutId(){
        return R.layout.fragment_third;
    }

    public void initTitle() {
        viewHolder =new TitleBarViewHolder(this);

        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titlename.setText(R.string.message);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));


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
        commonPresenter=new CommonPresenter<MessageBean>(this){};
        setAdapter(new MessageAdapter(this));
        commonPresenter.getCommonData(MESSTYPE,queryBean, DC.xiaoxilist,true);
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        queryBean.setPage(1);
        commonPresenter.getCommonData(MESSTYPE,queryBean, DC.xiaoxilist,true);
    }

    @Override
    public void asyLoadingMoreData() {
        commonPresenter.getCommonData(MESSMORETYPE,queryBean, DC.xiaoxilist,true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action:
                this.finish();
                break;
        }
    }

    @Override
    public void success(Object o, int type) {
        if (type==MESSTYPE){
            queryBean.setPage(2);
            getAdapter().updateData(o);
        }else if (type==MESSMORETYPE){
            ArrayList<MessageBean> temp= (ArrayList<MessageBean>) o;
            if (temp!=null && temp.size()>0){
                queryBean.page++;
            }
            getAdapter().loadMoreData(o);
        }
        stopLoadingAnimation();
    }

    @Override
    public void failure(Object o, int type) {

        Toast.makeText(this,""+o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
    }
}
