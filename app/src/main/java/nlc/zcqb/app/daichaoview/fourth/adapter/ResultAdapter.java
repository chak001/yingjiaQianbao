package nlc.zcqb.app.daichaoview.fourth.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.TitleViewHolder;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.CompanyViewHolder;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.ResultViewHolder;
import nlc.zcqb.app.daichaoview.fourth.bean.ZhenduanResultBean;
import nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.util.Utils;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class ResultAdapter extends BaseTypeAdapter{
    private final static  int RESULT=1111;
    private final static  int TITLE1= 11111;
    private final static  int PINGTAI=11113;
    ArrayList<String> pingtai=new ArrayList<>();
    private ZhenduanResultBean resultBean=new ZhenduanResultBean();
    private Integer credit=0;

    public ResultAdapter(Context mContext) {
        super(mContext);
        pingtai= Utils.getList(8);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case RESULT:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item,parent,false);
                viewHolder=new ResultViewHolder(view);
                break;
            case PINGTAI:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.firstfragment_item2,parent,false);
                viewHolder=new CompanyViewHolder(view);
                break;
            case TITLE1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_title,parent,false);
                viewHolder=new TitleViewHolder(view,mContext.getResources().getString(R.string.sub_title2));
                break;
             default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position==0){
            ResultViewHolder viewHolder =(ResultViewHolder) holder;
            viewHolder.discView.setValue(credit,1000);
        }else if (position==1){
            //设置标题
        }else {
            CompanyViewHolder viewHolder =(CompanyViewHolder) holder;
            viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.jumpIn(mContext,new Bundle(),DaiKuanDetailActivity.class);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int totalcount=2;
        if (pingtai.size()!=0){
            totalcount=totalcount+pingtai.size();
        }
        return totalcount;
    }


    @Override
    public int getTypeByPosition(int position) {
        if (position==0){
            return RESULT;
        }
        if (position==1){
            return TITLE1;
        }
        return PINGTAI;
    }

    @Override
    public void updateData(Object object) {
        if (object instanceof ZhenduanResultBean){
            this.resultBean= (ZhenduanResultBean) object;
            this.credit=resultBean.getCredit();
        }
        notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(Object object) {

    }

    @Override
    public void TypeFactory(Object object, int type,int UpdateType) {

    }


}
