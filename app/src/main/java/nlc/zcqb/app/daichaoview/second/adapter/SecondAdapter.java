package nlc.zcqb.app.daichaoview.second.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.ViewHolder2;
import nlc.zcqb.app.daichaoview.first.bean.RecommendBean;
import nlc.zcqb.app.daichaoview.second.adapter.viewholder.ViewHolder1;
import nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.util.Utils;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.KEY_ID;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class SecondAdapter extends BaseTypeAdapter{

    private final static  int PINGTAI=11113;
    ArrayList<RecommendBean> pingtai=new ArrayList<>();

    public SecondAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case PINGTAI:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.firstfragment_item2,parent,false);
                viewHolder=new ViewHolder1(view);
                break;
             default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecommendBean bean=pingtai.get(position);
        ViewHolder1 viewHolder1=(ViewHolder1)holder;
        viewHolder1.imageView.setImageDrawable(null);
        HttpUtil.loadimage(bean.getLogo(),viewHolder1.imageView,mContext);
        viewHolder1.title.setText(bean.getTitle());
        viewHolder1.desc.setText(bean.getA_line());
        viewHolder1.num.setText(bean.getLo_number()+"人已贷");
        viewHolder1.jine.setText( bean.getMinMoney() +"-"+ bean.getMaxMoney());
        viewHolder1.shijian.setText(bean.getLo_time());
        viewHolder1.lilv.setText(bean.getMonrate()+"%");
        viewHolder1.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(KEY_ID,bean.getId());
                ARouter.jumpIn(mContext,bundle,DaiKuanDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        int totalcount=0;
        if (pingtai.size()!=0){
            totalcount=totalcount+pingtai.size();
        }
        return totalcount;
    }


    @Override
    public int getTypeByPosition(int position) {
       return PINGTAI;
    }

    @Override
    public void updateData(Object object) {
        if (object==null){
            pingtai.clear();
        }else {
            pingtai=(ArrayList<RecommendBean>) object;
        }
        notifyDataSetChanged();

    }

    @Override
    public void loadMoreData(Object object) {
        if (object!=null){
            ArrayList<RecommendBean> temp=(ArrayList<RecommendBean>) object;
            pingtai.addAll(temp);
            notifyDataSetChanged();
        }
    }

    @Override
    public void TypeFactory(Object object, int type,int UpdateType) {

    }


}
