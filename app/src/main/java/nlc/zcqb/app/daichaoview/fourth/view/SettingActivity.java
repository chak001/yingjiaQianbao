package nlc.zcqb.app.daichaoview.fourth.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.login.FixPwdActivity;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.baselibrary.event.CommandEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.baseview.WebViewActivity;
import nlc.zcqb.baselibrary.util.AppVersionUtil;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener,CommonView{
    private TitleBarViewHolder viewHolder;
    private TextView fixpwd,about,logout,clear;
    private CommonSimplePresenter aboutPresenter;
    public final static int ABLOUTTYPE=25;
    private String aboutUrl="";
    private TextView version;

    public void initTitle() {
        viewHolder =new TitleBarViewHolder(this);

        viewHolder.rootview.setBackgroundColor(getResources().getColor(R.color.white));
        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titlename.setText(R.string.setting);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));

        viewHolder.titleBg.setBackgroundResource(R.color.white);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusColor(R.color.mosttransparentgray);
        }
    }

    public int getLayoutId(){
        return R.layout.activity_setting;
    }

    @Override
    public void InitMenu() {
        clear=(TextView) findViewById(R.id.clear);
        logout=(TextView) findViewById(R.id.logout);
        fixpwd=(TextView) findViewById(R.id.fixpwd);
        setTextDraw(R.mipmap.enter,RIGHT,16,fixpwd);
        about=(TextView)findViewById(R.id.about);
        setTextDraw(R.mipmap.enter,RIGHT,16,about);
        fixpwd.setOnClickListener(this);
        about.setOnClickListener(this);
        logout.setOnClickListener(this);
        clear.setOnClickListener(this);

        version=findViewById(R.id.version);
        version.setText("V"+ AppVersionUtil.getVerName(this));
    }

    @Override
    public void InitOthers() {
        aboutPresenter=new CommonSimplePresenter(this);
        addPresenter(aboutPresenter);
        aboutPresenter.getCommonData(ABLOUTTYPE,null, DC.about,"content");
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
            case R.id.fixpwd:
                ARouter.jumpIn(this,new Bundle(), FixPwdActivity.class);
                break;
            case R.id.about:
                Bundle bundle=new Bundle();
                bundle.putString(WebViewActivity.URL,aboutUrl);
                bundle.putString(WebViewActivity.TITLE,"关于我们");
                ARouter.jumpIn(this,bundle, WebViewActivity.class);
                break;
            case R.id.logout:
                MyApplication.mUser.clearUser();
                EventBus.getDefault().post(new CommandEvent(CommandEvent.CLEAR_USER,getRunningActivityName()));
                Toast.makeText(this,"已退出登陆，请重新登陆！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clear:
                Toast.makeText(this,"清除缓存成功！",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void success(Object o, int type) {
        if (type==ABLOUTTYPE){
            aboutUrl= (String) o;
        }
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(this,""+o,Toast.LENGTH_SHORT).show();
    }
}
