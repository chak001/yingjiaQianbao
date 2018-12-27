package nlc.zcqb.app.daichaoview.second.view;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customview.util;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.IdBean;
import nlc.zcqb.app.daichaoview.common.UserIdBean;
import nlc.zcqb.app.daichaoview.second.adapter.DaiKuanDetailAdapter;
import nlc.zcqb.app.daichaoview.second.bean.ApplyBean;
import nlc.zcqb.app.daichaoview.second.bean.MoneyBean;
import nlc.zcqb.app.daichaoview.second.bean.NumberBean;
import nlc.zcqb.app.daichaoview.second.bean.PingTaiDetailBean;
import nlc.zcqb.baselibrary.event.CommandEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseListActivity;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.baseview.WebViewActivity;
import nlc.zcqb.baselibrary.util.StringUtils;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class DaiKuanDetailActivity extends BaseListActivity implements CommonView,View.OnClickListener{
    private TextView left_text,right_text;
    private RelativeLayout leftWrap;
    private CommonPresenter<PingTaiDetailBean> detailresenter;
    private CommonSimplePresenter SPresenter;
    private CommonPresenter<MoneyBean> moneyPresenter;
    private CommonPresenter<NumberBean>  numPresenter;
    public final static int DETAILTYPE=10;
    public final static int ISCOLLECTION=12;
    public final static int GETQQ=13;
    public final static int GETMONEYLIST=22;
    public final static int GETNUMYLIST=23;
    public final static int ADDHISTORY=24;
    public final static int ADDAPPLY=27;

    private String pintTaiId;
    public final static String KEY_ID="KEY_ID";
    public final static int COLLECTION=11;
    private String kefuQQ;
    private CommonSimplePresenter collectionPresenter;
    private CommonSimplePresenter isCollectedPresenter;
    private UserIdBean userIdBean;
    private IdBean idBean;
    private Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_daikuandetail;
    }

    @Override
    public void initTitle() {

        left_text=(TextView) findViewById(R.id.left_text);
        leftWrap=(RelativeLayout) findViewById(R.id.left_wrapper);
        Drawable drawable=this.getResources().getDrawable(R.mipmap.lianxikefu);
        drawable.setBounds(0,0, util.dip2px(this,18),util.dip2px(this,18));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        left_text.setCompoundDrawables(drawable,null,null,null);//只放左边

        leftWrap.setOnClickListener(this);
        right_text=(TextView) findViewById(R.id.right_text);
        right_text.setOnClickListener(this);
    }

    @Override
    public void InitOthers(){
        bundle=new Bundle();
        idBean=new IdBean(pintTaiId);
        //初始化查询bean
        userIdBean=new UserIdBean(MyApplication.mUser.getUser_id(),pintTaiId);

        setAdapter(new DaiKuanDetailAdapter(this));
        //平台详情presenter
        detailresenter=new CommonPresenter<PingTaiDetailBean>(this){};
        //收藏相关的presenter
        collectionPresenter=new CommonSimplePresenter(this);
        isCollectedPresenter=new CommonSimplePresenter(this);
        SPresenter=new CommonSimplePresenter(this);
        numPresenter=new CommonPresenter<NumberBean>(this){};
        moneyPresenter=new CommonPresenter<MoneyBean>(this){};

        addPresenter(collectionPresenter);
        addPresenter(isCollectedPresenter);
        addPresenter(detailresenter);
        addPresenter(SPresenter);
        addPresenter(numPresenter);
        addPresenter(moneyPresenter);

        if (ARouter.isLogin()){
            isCollectedPresenter.getCommonData(ISCOLLECTION,userIdBean,DC.shifoushoucang,"state");
        }
        idBean.setId(pintTaiId);
        detailresenter.getCommonData(DETAILTYPE, idBean,DC.daikuanxiangqing,false);
        SPresenter.getCommonData(GETQQ,null,DC.kefuqq,"num");
        numPresenter.getCommonData(GETNUMYLIST,idBean,DC.daikuanqixian,true);
        moneyPresenter.getCommonData(GETMONEYLIST,idBean,DC.daikuanjine,true);
        String  userid=MyApplication.mUser.getUser_id();
        if (MyApplication.mUser.isLogin() && StringUtils.isNullOrEmpty(userid)) {
            isCollectedPresenter.getCommonData(ADDHISTORY, userIdBean, DC.tianjiajilu, "code");
        }
    }

    @Override
    public void parseIntent() {
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            pintTaiId= bundle.getString(KEY_ID,"0");
        }

    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        idBean.setId(pintTaiId);
        detailresenter.getCommonData(DETAILTYPE, idBean,DC.daikuanxiangqing,false);
        SPresenter.getCommonData(GETQQ,null,DC.kefuqq,"num");
    }

    @Override
    public void asyLoadingMoreData() {

    }

    @Override
    public void success(Object o, int type) {
        if (type==DETAILTYPE){
            PingTaiDetailBean bean= (PingTaiDetailBean) o;
            bundle.putString(WebViewActivity.URL,bean.getYou_url());
            bundle.putString(WebViewActivity.TITLE,bean.getTitle());
            getAdapter().updateData(o);
        }else if (type==COLLECTION){
            getAdapter().TypeFactory(o,COLLECTION, BaseTypeAdapter.REFRESH);
        }else if (type==ISCOLLECTION){
            getAdapter().TypeFactory(o,ISCOLLECTION, BaseTypeAdapter.REFRESH);
        }else if (type==GETQQ){
            if (StringUtils.isNumeric((String) o)){
                kefuQQ= (String) o;
            }else {
                kefuQQ= DC.tempQQ;
            }
        }else if (type==GETMONEYLIST){
            getAdapter().TypeFactory(o,GETMONEYLIST,BaseTypeAdapter.REFRESH);
        }else if (type==GETNUMYLIST){
            getAdapter().TypeFactory(o,GETNUMYLIST,BaseTypeAdapter.REFRESH);
        }else if (type==ADDHISTORY){
            if ("100".equals( o)){
                Log.e("ADDHISTORY","成功添加历史！");
            }
        }else if (type==ADDAPPLY){
            if ("100".equals( o)){
                Log.e("ADDAPPLY","成功添加申请记录！");
            }
        }
        stopLoadingAnimation();
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(this, (String) o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_wrapper:
                ARouter.jumpToQQ(this, kefuQQ);
//                if (kefuQQ.length()>5) {
//                    ARouter.jumpToQQ(this, DC.tempQQ);
//                }else {
//                    Toast.makeText(this,"暂未获取到客服QQ或者刷新重新获取！",Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.right_text:
                if (MyApplication.mUser.isLogin() && MyApplication.mUser.getUser_id()!=null){
                    ApplyBean bean=((DaiKuanDetailAdapter)getAdapter()).getApplyDetail();
                    bean.setUsid(MyApplication.mUser.getUser_id());
                    SPresenter.getCommonData(ADDAPPLY,bean,DC.daikuanjilu,"code");
                }

                right_text.setEnabled(false);
                ARouter.jumpIn(this,bundle, WebViewActivity.class);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        right_text.setEnabled(true);
                    }
                },300);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(CommandEvent event) {
        if (COLLECTION==Integer.valueOf(event.getType())){
            collectionPresenter.getCommonData(COLLECTION,userIdBean,DC.shoucang,"state");
        }else if (ISCOLLECTION==Integer.valueOf(event.getType())){
            isCollectedPresenter.getCommonData(ISCOLLECTION,userIdBean,DC.shifoushoucang,"state");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==COLLECTION){
            if (resultCode==RESULT_OK){
                userIdBean.setUsid(MyApplication.mUser.getUser_id());
                isCollectedPresenter.getCommonData(ISCOLLECTION,userIdBean,DC.shifoushoucang,"state");
            }
        }
    }
}
