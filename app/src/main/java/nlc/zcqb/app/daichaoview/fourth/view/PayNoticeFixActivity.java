package nlc.zcqb.app.daichaoview.fourth.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.IdBean;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.NoticeDetailViewHolder;
import nlc.zcqb.app.daichaoview.fourth.bean.FixNoticeBean;
import nlc.zcqb.app.daichaoview.fourth.bean.NoticeBean;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.baselibrary.event.CommandEvent;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseActivity;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.KEY_ID;
import static nlc.zcqb.baselibrary.baseview.BaseTypeAdapter.REFRESH;

/**
 * Created by lvqiu on 2018/10/16.
 */

public class PayNoticeFixActivity extends BaseActivity implements View.OnClickListener,CommonView{
    public final static String TAG="PayNoticeFixActivity";

    private NoticeDetailViewHolder noticeDetailViewHolder;
    private NoticeBean noticeBean=new NoticeBean();
    private CommonPresenter<NoticeBean> commonPresenter;
    private CommonSimplePresenter simplePresenter;
    private final static int NOTICETYPE=11;
    private final static int SAVETYPE=12;
    private final static int DELETETYPE=13;
    private Button create;
    private TextView delete;
    private boolean isDoing=false;
    IdBean idBean =new IdBean();

    public void initTitle() {

        viewHolder =new TitleBarViewHolder(this);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusColor(R.color.mosttransparentgray);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_fixpaynotice;
    }

    @Override
    public void InitMenu() {
        initTitle();

        noticeDetailViewHolder=new NoticeDetailViewHolder(this);
        create=(Button) findViewById(R.id.create);
        delete=(TextView) findViewById(R.id.delete);

        if (idBean.getId().equalsIgnoreCase("0")){
            viewHolder.titlename.setText("新建提醒");
            delete.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.titlename.setText("修改提醒");
            delete.setOnClickListener(this);
        }
        create.setOnClickListener(this);
    }

    @Override
    public void InitOthers() {
        simplePresenter=new CommonSimplePresenter(this);
        commonPresenter=new CommonPresenter<NoticeBean>(this){};
        addPresenter(commonPresenter);
        addPresenter(simplePresenter);
        if (!idBean.getId().equalsIgnoreCase("0")) {
            commonPresenter.getCommonData(NOTICETYPE, idBean, DC.chaxunhuankuandetail, false);
        }
    }

    @Override
    public void parseIntent() {
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            //不传递id那么就是新建提醒
            String id=bundle.getString(KEY_ID,"0");
            idBean.setId(id);
            noticeBean.setId(id);
        }
    }

    @Override
    public void onClick(View v) {
        if (isDoing){
            return;
        }
        switch (v.getId()){
            case R.id.back_action:
                this.finish();
                break;
            case R.id.create:

                noticeBean= noticeDetailViewHolder.getBean();
                FixNoticeBean bean= NoticeDetailViewHolder.TransferBean(noticeBean);
                bean.setId(idBean.getId());
                bean.setUsid(mUser.getUser_id());
                if (noticeDetailViewHolder.checkValidate()) {
                    simplePresenter.getCommonData(SAVETYPE, bean, DC.xiugaitixin, "code");
                    isDoing=true;
                }else {
                    Toast("你有未填项目，请补充完整！");
                }

                break;
            case R.id.delete:
                noticeBean= noticeDetailViewHolder.getBean();
                noticeBean.setId(idBean.getId());
                {
                    simplePresenter.getCommonData(DELETETYPE, idBean, DC.deletetixin, "code");
                    isDoing = true;
                }
                break;
        }
    }

    @Override
    public void success(Object o, int type) {
        isDoing=false;
        if (type==NOTICETYPE){
            if (o!=null){
                noticeBean= (NoticeBean) o;
            }
            noticeDetailViewHolder.initView(noticeBean);
        }else if (type==SAVETYPE && "100".equalsIgnoreCase((String) o)){
            Toast.makeText(this,"保存提醒成功！",Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new CommandEvent(TAG,REFRESH+""));
            this.finish();
        }else if (type==DELETETYPE && "100".equalsIgnoreCase((String) o)){
            Toast.makeText(this,"删除提醒成功！",Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new CommandEvent(TAG,REFRESH+""));
            this.finish();
        }
    }

    @Override
    public void failure(Object o, int type) {
        isDoing=false;
        Toast.makeText(this,(String)o,Toast.LENGTH_SHORT).show();
    }

}
