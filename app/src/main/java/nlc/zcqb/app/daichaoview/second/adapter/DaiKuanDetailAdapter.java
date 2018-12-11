package nlc.zcqb.app.daichaoview.second.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.login.LoginActivity;
import nlc.zcqb.app.daichaoview.second.adapter.viewholder.DKDViewHolder1;
import nlc.zcqb.app.daichaoview.second.adapter.viewholder.DKDViewHolder2;
import nlc.zcqb.app.daichaoview.second.bean.MoneyBean;
import nlc.zcqb.app.daichaoview.second.bean.NumberBean;
import nlc.zcqb.app.daichaoview.second.bean.PayMoneyQueryBean;
import nlc.zcqb.app.daichaoview.second.bean.PingTaiDetailBean;
import nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity;
import nlc.zcqb.app.event.CommandEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.callback.ClickCallback;
import nlc.zcqb.baselibrary.util.StringUtils;
import nlc.zcqb.baselibrary.util.Utils;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.COLLECTION;
import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.GETMONEYLIST;
import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.GETNUMYLIST;
import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.ISCOLLECTION;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class DaiKuanDetailAdapter extends BaseTypeAdapter implements CommonView{
    private PingTaiDetailBean detailBean=null;
    private int isCollected=2;//1已收藏 2未收藏
    private ArrayList<String> jinelist=new ArrayList<>();
    private ArrayList<String> riqilist=new ArrayList<>();
    private CommonSimplePresenter payMoneyPresenter;
    private final static int PAYMONEYTYPE=24;
    private String payMoney,totalMoney,totalTerm;
    private PayMoneyQueryBean queryBean=new PayMoneyQueryBean("1000","1","0.03");

    public DaiKuanDetailAdapter(Context mContext) {
        super(mContext);
        payMoneyPresenter=new CommonSimplePresenter(this);
        ((BaseActivity)mContext).addPresenter(payMoneyPresenter);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.daikuandetail_item1,parent,false);
                viewHolder=new DKDViewHolder1(view);
                break;
            default:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.daikuandetail_item2,parent,false);
                viewHolder=new DKDViewHolder2(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (detailBean==null){
            return;
        }
        if (position==0){
            final DKDViewHolder1 viewHolder1=(DKDViewHolder1) holder;
            viewHolder1.back.setVisibility(View.VISIBLE);
            viewHolder1.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)mContext).finish();
                }
            });
            viewHolder1.title.setText(mContext.getString(R.string.daikuanxiangqing));
            viewHolder1.left_func.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DaiKuanDetailActivity)mContext).showChooseDialog(mContext.getString(R.string.choose_jine), jinelist, new ClickCallback() {
                        @Override
                        public void click(int position) {
                            viewHolder1.totalnum.setText(jinelist.get(position));
                            totalMoney=jinelist.get(position);
                            queryBean.setMoney(jinelist.get(position));
                            payMoneyPresenter.getCommonData(PAYMONEYTYPE,queryBean, DC.xuhuanjine,"money");
                        }
                    });
                }
            });
            viewHolder1.right_func.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DaiKuanDetailActivity)mContext).showChooseDialog(mContext.getString(R.string.choose_riqi), riqilist, new ClickCallback() {
                        @Override
                        public void click(int position) {
                            viewHolder1.deadline.setText(riqilist.get(position));
                            totalTerm=riqilist.get(position);
                            queryBean.setNumber(riqilist.get(position));
                            payMoneyPresenter.getCommonData(PAYMONEYTYPE,queryBean, DC.xuhuanjine,"money");
                        }
                    });
                }
            });
            viewHolder1.peplenum.setText(detailBean.getLo_number()+"人已放贷");
            viewHolder1.discribetion.setText(detailBean.getA_line());
            viewHolder1.companyname.setText(detailBean.getTitle());
            HttpUtil.loadimage(detailBean.getLogo(),viewHolder1.logo,mContext);

            viewHolder1.totalnum.setText(totalMoney+"元");
            viewHolder1.deadline.setText(totalTerm+"月");

            viewHolder1.shijian.setText(detailBean.getLo_time());
            //对于int类型设置，要把它变为string，要不然会把它当初resourced使用
            viewHolder1.huandai.setText(payMoney+"");
            viewHolder1.lilv.setText(detailBean.getMonrate()+"%");

            if (isCollected==1){
                viewHolder1.favorite.setImageResource(R.mipmap.shoucang_focused);
            }else if (isCollected==2){
                viewHolder1.favorite.setImageResource(R.mipmap.shoucang);
            }

            viewHolder1.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  userid=MyApplication.mUser.getUser_id();
                    if (!MyApplication.mUser.isLogin() || StringUtils.isNullOrEmpty(userid)){
                        ARouter.jumpForResult(mContext,new Bundle(), LoginActivity.class,COLLECTION);
                    }else {
                        EventBus.getDefault().post(new CommandEvent(String.valueOf(COLLECTION),null));
                    }
                }
            });
        }
        if (position==1){
            DKDViewHolder2 viewHolder=(DKDViewHolder2) holder;
            viewHolder.subTitle.setText(mContext.getString(R.string.shenqingzige));
            viewHolder.content.setText(detailBean.getQuali());
        }
        if (position==2){
            DKDViewHolder2 viewHolder=(DKDViewHolder2) holder;
            viewHolder.subTitle.setText(mContext.getString(R.string.chanpingjieshao));
            viewHolder.content.setText(detailBean.getIntrod());
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    @Override
    public int getTypeByPosition(int position) {
        return position;
    }

    @Override
    public void updateData(Object object) {
        if (object!=null){
            detailBean= (PingTaiDetailBean) object;
            payMoney= String.valueOf(detailBean.getReMoney());
            totalMoney= detailBean.getMoney();
            totalTerm=detailBean.getTerm();
            queryBean=new PayMoneyQueryBean(detailBean.getMoney(),detailBean.getTerm(),detailBean.getMonrate());
            notifyDataSetChanged();
        }
    }

    @Override
    public void loadMoreData(Object object) {

    }

    @Override
    public void TypeFactory(Object object,int type ,int UpdateType) {
        if (type==COLLECTION){
            isCollected = Integer.valueOf((String) object);
        }else if (type==ISCOLLECTION){
            isCollected = Integer.valueOf((String) object);
        }else if (type== GETMONEYLIST){
            jinelist= getMoneys((ArrayList<MoneyBean>) object);
        }else if (type==GETNUMYLIST){
            riqilist= getNums((ArrayList<NumberBean>) object);
        }
        notifyDataSetChanged();
    }


    public ArrayList<String> getMoneys(ArrayList<MoneyBean> list){
        if (list==null || list.size()==0){
            return new ArrayList<>();
        }
        ArrayList<String> temp=new ArrayList<>();
        for (MoneyBean bean:list) {
            temp.add(bean.getMoney());
        }
        return temp;
    }


    public ArrayList<String> getNums(ArrayList<NumberBean> list){
        if (list==null || list.size()==0){
            return new ArrayList<>();
        }
        ArrayList<String> temp=new ArrayList<>();
        for (NumberBean bean:list) {
            temp.add(bean.getNumber());
        }
        return temp;
    }

    @Override
    public void success(Object o, int type) {
        if (type==PAYMONEYTYPE ){
            payMoney= (String) o;
            notifyDataSetChanged();
        }
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(mContext,""+o,Toast.LENGTH_SHORT).show();
    }
}
