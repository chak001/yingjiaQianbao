package nlc.zcqb.app.daichaoview.third.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.third.adapter.viewholder.ViewHolder1;
import nlc.zcqb.app.daichaoview.third.bean.GongLueItemBean;
import nlc.zcqb.app.daichaoview.third.view.GonglueDetailActivity;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.util.Utils;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class ThirdAdapter extends BaseTypeAdapter{

    private final static  int GONGLUE=11114;

    ArrayList<GongLueItemBean> gonglue=new ArrayList<>();


    public ThirdAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case GONGLUE:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.firstfragment_item3,parent,false);
                viewHolder=new ViewHolder1(view);
                break;
             default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder1 viewHolder1=(ViewHolder1) holder;
        HttpUtil.loadimage(gonglue.get(position).getImg(),viewHolder1.backgroundimage,mContext);
        viewHolder1.title.setText(""+gonglue.get(position).getTitle());
        viewHolder1.backgroundimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(ARouter.KEY,gonglue.get(position).getId());
                ARouter.jumpIn(mContext,bundle, GonglueDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        int totalcount=0;
        if (gonglue.size()!=0){
            totalcount=totalcount+gonglue.size();
        }
        return totalcount;
    }


    @Override
    public int getTypeByPosition(int position) {
       return GONGLUE;
    }

    @Override
    public void updateData(Object object) {
        if (object!=null){
            gonglue=(ArrayList<GongLueItemBean>) object ;
            notifyDataSetChanged();
        }
    }

    @Override
    public void loadMoreData(Object object) {

    }

    @Override
    public void TypeFactory(Object object,int type ,int UpdateType) {

    }


}
