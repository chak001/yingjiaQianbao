package nlc.zcqb.app.daichaoview.third.view;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.IdBean;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.daichaoview.third.bean.GongLueDetailBean;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.baseview.BaseActivity;

/**
 * Created by lvqiu on 2018/12/1.
 */

public class GonglueDetailActivity extends BaseActivity implements View.OnClickListener,CommonView{
    CommonPresenter  presenter;
    private static final int GETDETAIL=111;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gongluedetail;
    }

    @Override
    public void InitMenu() {
        viewHolder=new TitleBarViewHolder(this);

        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titlename.setText(R.string.gongluexiangqing);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            viewHolder.rootview.setBackgroundColor(getResources().getColor(R.color.hintcolor));
        }
    }

    private TextView title,content;
    private ImageView icon;
    @Override
    public void InitOthers() {
        title=(TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
        content=(TextView) findViewById(R.id.content);
        icon=(ImageView) findViewById(R.id.icon);

        presenter=new CommonPresenter<GongLueDetailBean>(this){};
        presenter.getCommonData(GETDETAIL,new IdBean(id), DC.gongluexiangqing,false);
    }



    String id="";
    @Override
    public void parseIntent() {
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null) {
            id = (String) bundle.getSerializable(ARouter.KEY);
            if (id == null) {
                id = "";
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action:
                this.finish();
                break;

        }
    }

    public void updateView(GongLueDetailBean bean){
        viewHolder.titlename.setText(""+bean.getTitle());
        content.setText(Html.fromHtml(bean.getContent()+""));
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        HttpUtil.loadimage(bean.getImg(),icon,this);
    }

    @Override
    public void success(Object o, int type) {
        if (GETDETAIL==type){
            updateView((GongLueDetailBean)o);
        }
    }

    @Override
    public void failure(Object o, int type) {
        Toast((String) o);
    }
}
