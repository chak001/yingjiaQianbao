package nlc.zcqb.app.daichaoview.first.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.MoreViewHolder;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.TitleViewHolder;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.ViewHolder1;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.ViewHolder2;
import nlc.zcqb.app.daichaoview.first.adapter.viewholder.ViewHolder3;
import nlc.zcqb.app.daichaoview.first.bean.RecommendBean;
import nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity;
import nlc.zcqb.app.daichaoview.third.bean.GongLueItemBean;
import nlc.zcqb.app.daichaoview.third.view.GonglueDetailActivity;
import nlc.zcqb.app.event.CommandEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.util.Utils;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.KEY_ID;
import static nlc.zcqb.app.daichaoview.third.fragment.Thirdfragment.GONGLUETYPE;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class FirstAdapter extends BaseTypeAdapter{
    private final static  int TITLE1= 11111;
    private final static  int TITLE2= 11112;
    private final static  int MORE1= 22222;
    private final static  int MORE2= 22223;
    private final static  int PINGTAI=11113;
    private final static  int GONGLUE=11114;
    private final static  int GONGGAO=11110;

    private String imageurl,bullet;
    ArrayList<RecommendBean> pingtais=new ArrayList<>();
    ArrayList<GongLueItemBean> gonglue=new ArrayList<>();


    public FirstAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case GONGGAO:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.firstfragment_item1,parent,false);
                viewHolder=new ViewHolder1(view);
                break;
            case PINGTAI:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.firstfragment_item2,parent,false);
                viewHolder=new ViewHolder2(view);
                break;
            case GONGLUE:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.firstfragment_item3,parent,false);
                viewHolder=new ViewHolder3(view);
                break;
            case TITLE1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_title,parent,false);
                viewHolder=new TitleViewHolder(view,mContext.getResources().getString(R.string.sub_title2));
                break;
            case TITLE2:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_title,parent,false);
                viewHolder=new TitleViewHolder(view,mContext.getResources().getString(R.string.sub_title3));
                break;
            case MORE1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_more,parent,false);
                viewHolder=new MoreViewHolder(view,"1");
                break;
            case MORE2:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_more,parent,false);
                viewHolder=new MoreViewHolder(view,"2");
                break;
             default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getTypeByPosition(position)){
            case GONGGAO:
                ViewHolder1 viewHolder1=(ViewHolder1)holder;
                viewHolder1.title.setText(bullet);
                HttpUtil.loadimage(imageurl,viewHolder1.backgroundimage,mContext);
                break;
            case PINGTAI:
                final RecommendBean bean=pingtais.get(position-2);
                ViewHolder2 viewHolder2=(ViewHolder2)holder;
                viewHolder2.imageView.setImageDrawable(null);
                HttpUtil.loadimage(bean.getLogo(),viewHolder2.imageView,mContext);
                viewHolder2.title.setText(bean.getTitle());
                viewHolder2.desc.setText(bean.getA_line());
                viewHolder2.num.setText(bean.getLo_number()+"人已贷");
                viewHolder2.jine.setText( bean.getMinMoney() +"-"+ bean.getMaxMoney());
                viewHolder2.shijian.setText(bean.getLo_time());
                viewHolder2.lilv.setText(bean.getMonrate()+"%");
                viewHolder2.rootview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString(KEY_ID,bean.getId());
                        ARouter.jumpIn(mContext,bundle,DaiKuanDetailActivity.class);
                    }
                });
                break;
            case MORE1:
                final MoreViewHolder moreViewHolder1= (MoreViewHolder) holder;
                moreViewHolder1.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new CommandEvent(CommandEvent.CHANGEPAGE,moreViewHolder1.tag));
                    }
                });
                break;
            case MORE2:
                final MoreViewHolder moreViewHolder2= (MoreViewHolder) holder;
                moreViewHolder2.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new CommandEvent(CommandEvent.CHANGEPAGE,moreViewHolder2.tag));
                    }
                });
                break;
            case GONGLUE:

                final int start=getGonggaoStart();
                if (position-start>=gonglue.size()){
                    break;
                }
                ViewHolder3 viewHolder3=(ViewHolder3) holder;
                HttpUtil.loadimage(gonglue.get(position-start).getImg(),viewHolder3.backgroundimage,mContext);
                viewHolder3.title.setText(""+gonglue.get(position-start).getTitle());
                viewHolder3.backgroundimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString(ARouter.KEY,gonglue.get(position-start).getId());
                        ARouter.jumpIn(mContext,bundle, GonglueDetailActivity.class);
                    }
                });
                break;
            default:break;
        }
    }


    public  int getGonggaoStart(){
        int totalcount=1;
        if (pingtais.size()!=0){
            totalcount=totalcount+2+pingtais.size();
        }
        return totalcount+1;
    }

    @Override
    public int getItemCount() {
        int totalcount=1;
        if (pingtais.size()!=0){
            totalcount=totalcount+2+pingtais.size();
        }
        if (gonglue.size()!=0){
            totalcount=totalcount+2+gonglue.size();
        }
        return totalcount;
    }


    @Override
    public int getTypeByPosition(int position) {
        if (position==1){
            return TITLE1;
        }
        if (position==1+pingtais.size()+1){
            return MORE1;
        }
        if (position==1+pingtais.size()+2){
            return TITLE2;
        }
        if (position==1+pingtais.size()+2+gonglue.size()+1){
            return MORE2;
        }
        if (position==0){
            return GONGGAO;
        }
        if (position>1 && position<pingtais.size()+2){
            return PINGTAI;
        }else {
            return GONGLUE;
        }
    }

    @Override
    public void updateData(Object object) {
        if (object!=null){
            ArrayList<RecommendBean> beans= (ArrayList<RecommendBean>) object;
            pingtais=beans;
            notifyDataSetChanged();
        }
    }

    public void updateBG(String imageurl){
        if (imageurl!=null && imageurl.length()>0) {
            this.imageurl = imageurl;
            notifyDataSetChanged();
            Log.e("updateBG",""+imageurl);
        }
    }

    public void updateBullet(String bullet){
        if (bullet!=null && bullet.length()>0) {
            this.bullet = bullet;
            notifyDataSetChanged();
        }
    }


    @Override
    public void loadMoreData(Object object) {

    }

    @Override
    public void TypeFactory(Object object, int type,int updateType) {
        if (type==GONGLUETYPE){
            if (updateType==REFRESH){
                gonglue= (ArrayList<GongLueItemBean>) object;
                notifyDataSetChanged();
            }
        }else if(type==GONGGAO){

        }
    }


}
