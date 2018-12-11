package nlc.zcqb.app.daichaoview.fourth.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waImageClip.activity.MediaPickHelper;

import org.greenrobot.eventbus.EventBus;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.ViewHolder1;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.ViewHolder2;
import nlc.zcqb.app.daichaoview.fourth.view.HistoryActivity;
import nlc.zcqb.app.daichaoview.fourth.view.MessageListActivity;
import nlc.zcqb.app.daichaoview.fourth.view.PayNoticeListActivity;
import nlc.zcqb.app.daichaoview.fourth.view.PersonalActivity;
import nlc.zcqb.app.daichaoview.fourth.view.SettingActivity;
import nlc.zcqb.app.daichaoview.fourth.view.ShouCangActivity;
import nlc.zcqb.app.daichaoview.fourth.view.XinYongActivity;
import nlc.zcqb.app.daichaoview.login.LoginActivity;
import nlc.zcqb.app.event.CommandEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.util.StringUtils;
import nlc.zcqb.wxlibrary.view.SendToWXActivity;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.GETQQ;
import static nlc.zcqb.app.event.CommandEvent.UPLOAD_ICON;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class FourthAdapter extends BaseTypeAdapter {
    private String QQ="";

    public FourthAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ff_item1,parent,false);
                viewHolder=new ViewHolder1(view);
                break;
            case 1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ff_item2,parent,false);
                viewHolder=new ViewHolder2(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position==0 ){
            ViewHolder1 viewHolder1=(ViewHolder1) holder;

            viewHolder1.favorate_wrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ARouter.jumpIn(mContext,new Bundle(),ShouCangActivity.class);
                }
            });
            viewHolder1.history_wrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(),HistoryActivity.class);
                }
            });
            viewHolder1.usericon.setImageResource(0);

            User user=MyApplication.mUser;
            if (user.isLogin() &&  StringUtils.NotNull(user.getUser_id())){
                viewHolder1.nickname.setText(""+StringUtils.getNullString(user.getNickname()));
                viewHolder1.desribe.setText(R.string.defaultdesc);
                HttpUtil.loadimage(DC.picServer+user.getIcon(),viewHolder1.usericon,mContext);
                viewHolder1.wrap_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.jumpIn(mContext,new Bundle(),PersonalActivity.class);
                    }
                });
                viewHolder1.usericon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new CommandEvent(UPLOAD_ICON,""));
                    }
                });
            }else {
                viewHolder1.nickname.setText(R.string.rightlogin);
                viewHolder1.desribe.setText(R.string.rightdesc);
                viewHolder1.usericon.setImageResource(R.mipmap.default_icon);
                viewHolder1.wrap_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.jumpIn(mContext,new Bundle(),LoginActivity.class);
                    }
                });
            }
        }else if (position==1){
            ViewHolder2 viewHolder2=(ViewHolder2) holder;
            viewHolder2.relativeLayouts[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(),PersonalActivity.class);
                }
            });
            viewHolder2.relativeLayouts[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(),PayNoticeListActivity.class);
                }
            });
            viewHolder2.relativeLayouts[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(),MessageListActivity.class);
                }
            });
            viewHolder2.relativeLayouts[3].setVisibility(View.GONE);
//            viewHolder2.relativeLayouts[3].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ARouter.jumpIn(mContext,new Bundle(),XinYongActivity.class);
//                }
//            });
            viewHolder2.relativeLayouts[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpToQQ(mContext, DC.tempQQ);
                }
            });
            viewHolder2.relativeLayouts[5].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(), SendToWXActivity.class);
                }
            });
            viewHolder2.relativeLayouts[6].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(),SettingActivity.class);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    @Override
    public int getTypeByPosition(int position) {
        return position;
    }

    @Override
    public void updateData(Object object) {
        TypeFactory(object,0,REFRESH);
    }

    @Override
    public void loadMoreData(Object object) {
        TypeFactory(object,0,LOADMORE);
    }

    @Override
    public void TypeFactory(Object object, int type,int UpdatetType) {
        if (object instanceof String){
            QQ= (String) object;
            notifyDataSetChanged();
        }else if(object instanceof User){
            notifyDataSetChanged();
        }
    }


}
