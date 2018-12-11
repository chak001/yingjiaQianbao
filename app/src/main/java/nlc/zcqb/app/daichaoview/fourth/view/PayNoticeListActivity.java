package nlc.zcqb.app.daichaoview.fourth.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.fourth.adapter.NoticeAdapter;
import nlc.zcqb.app.daichaoview.fourth.bean.NoticeBean;
import nlc.zcqb.app.daichaoview.fourth.bean.QueryBean;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.event.CommandEvent;
import nlc.zcqb.app.event.DataSynEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseListActivity;

import static nlc.zcqb.baselibrary.baseview.BaseTypeAdapter.REFRESH;

/**
 * Created by lvqiu on 2018/10/16.
 */

public class PayNoticeListActivity extends BaseListActivity implements View.OnClickListener,CommonView{
    private TitleBarViewHolder viewHolder;
    private TextView tipText;
    private ImageView tipimage;
    private Button checkbotton;
    private CommonPresenter<NoticeBean> commonPresenter;
    private final static int NOTICETYPE=112;
    private final static int NOTICEMORETYPE=113;
    private QueryBean queryBean=new QueryBean(1, MyApplication.mUser.getUser_id());

    public int getLayoutId(){
        return R.layout.activity_paynotice;
    }

    @Override
    public void initTitle() {
        viewHolder =new TitleBarViewHolder(this);

        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titlename.setText(R.string.huankuantixin);
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

        tipimage=(ImageView) findViewById(R.id.tips);
        tipText=(TextView) findViewById(R.id.tip_text);

        checkbotton=(Button) findViewById(R.id.checkbotton);
        checkbotton.setOnClickListener(this);

        commonPresenter=new CommonPresenter<NoticeBean>(this){};
        setAdapter(new NoticeAdapter(this));
        commonPresenter.getCommonData(NOTICETYPE,queryBean, DC.chaxunhuankuanlist,true );
    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        queryBean.page=1;
        commonPresenter.getCommonData(NOTICETYPE,queryBean, DC.chaxunhuankuanlist,true );
    }

    @Override
    public void asyLoadingMoreData() {
        commonPresenter.getCommonData(NOTICEMORETYPE,queryBean, DC.chaxunhuankuanlist,true );
    }


    @Override
    public void parseIntent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action:
                this.finish();
                break;
            case R.id.checkbotton:
                ARouter.jumpIn(this,new Bundle(),PayNoticeFixActivity.class);
                break;
        }
    }

    @Override
    public void success(Object o, int type) {
        if (type==NOTICETYPE){
            queryBean.page = 2;
            getAdapter().updateData(o);
        }else if (type==NOTICEMORETYPE){
            ArrayList<NoticeBean> temp= (ArrayList<NoticeBean>) o;
            if (temp!=null && temp.size()>0) {
                queryBean.page++;
                getAdapter().loadMoreData(o);
            }
        }
        stopLoadingAnimation();
        showTips();
    }

    @Override
    public void failure(Object o, int type) {
        if (type==NOTICETYPE){
            getAdapter().updateData(null);
        }
        Toast.makeText(this,""+o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
        showTips();
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(CommandEvent event) {
        if (event.getType().equalsIgnoreCase(PayNoticeFixActivity.TAG)){
            if (event.getCommand().equalsIgnoreCase(REFRESH+"")){
                asyRefreshData();
            }
        }
    }


    public void showTips(){
        int count=getAdapter().getItemCount();
        if (count!=0){
            tipimage.setVisibility(View.GONE);
            tipText.setVisibility(View.GONE);
        }else {
            tipimage.setVisibility(View.VISIBLE);
            tipText.setVisibility(View.VISIBLE);
        }
    }
}
