package nlc.zcqb.app.daichaoview.fourth.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.CompanyViewHolder;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.NoticeViewHolder;
import nlc.zcqb.app.daichaoview.fourth.bean.NoticeBean;
import nlc.zcqb.app.daichaoview.fourth.view.PayNoticeFixActivity;
import nlc.zcqb.app.daichaoview.second.adapter.viewholder.ViewHolder1;
import nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.util.Utils;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.KEY_ID;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class NoticeAdapter extends BaseTypeAdapter{

    private final static  int NOTICE=11113;
    ArrayList<NoticeBean> noticelist=new ArrayList<>();

    public NoticeAdapter(Context mContext) {
        super(mContext);
    }

    public NoticeAdapter(Context mContext, ArrayList<NoticeBean> noticelist) {
        super(mContext);
        this.noticelist=noticelist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case NOTICE:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item,parent,false);
                viewHolder=new NoticeViewHolder(view);
                break;
             default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoticeViewHolder viewHolder=(NoticeViewHolder) holder;
        final NoticeBean bean=noticelist.get(position);
        viewHolder.title.setText(bean.getTitle());
        viewHolder.fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(KEY_ID,bean.getId());
                ARouter.jumpIn(mContext,bundle, PayNoticeFixActivity.class);
            }
        });
        viewHolder.jiedaijine.setText(bean.getMoney());
        viewHolder.deadline.setText(bean.getDay());
        viewHolder.meiqihuankuan.setText(bean.getRe_money());
    }

    @Override
    public int getItemCount() {
        int totalcount=0;
        if (noticelist!=null && noticelist.size()!=0){
            totalcount=totalcount+noticelist.size();
        }
        return totalcount;
    }


    @Override
    public int getTypeByPosition(int position) {
       return NOTICE;
    }

    @Override
    public void updateData(Object object) {
        if (object==null){
            noticelist.clear();
        }else {
            noticelist= (ArrayList<NoticeBean>) object;
        }
        notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(Object object) {
        if (object!=null){
            noticelist.addAll((Collection<? extends NoticeBean>) object);
            notifyDataSetChanged();
        }
    }

    @Override
    public void TypeFactory(Object object,int type, int UpdateType) {

    }


}
