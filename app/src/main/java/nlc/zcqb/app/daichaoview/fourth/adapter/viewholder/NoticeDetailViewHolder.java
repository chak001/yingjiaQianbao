package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.fourth.bean.FixNoticeBean;
import nlc.zcqb.app.daichaoview.fourth.bean.NoticeBean;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.baseview.view.ChooseTextview;
import nlc.zcqb.baselibrary.baseview.view.TitleEditview;
import nlc.zcqb.baselibrary.callback.TimeClickCallback;
import nlc.zcqb.baselibrary.util.StringUtils;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class NoticeDetailViewHolder  {
    public TitleEditview title,jiedaijine,deadline,meiqihuankuan;
    public ChooseTextview shouci,zhouqi,tiqian,tixing;;
    private BaseActivity baseActivity;
    private String[] zhouqiList =new String[]{"每周","每两周","每月","每两月"};
    private String[] tiqianList =new String[]{"一天","两天","三天"};
    public NoticeDetailViewHolder(Activity itemView) {
        baseActivity= (BaseActivity) itemView;
        title=(TitleEditview) itemView.findViewById(R.id.ed1);
        jiedaijine=(TitleEditview) itemView.findViewById(R.id.ed2);
        deadline=(TitleEditview) itemView.findViewById(R.id.ed3);
        meiqihuankuan=(TitleEditview) itemView.findViewById(R.id.ed4);

        shouci=(ChooseTextview) itemView.findViewById(R.id.te1);
        shouci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showPicker(BaseActivity.YEAR_DATE, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        shouci.setText(time);
                    }
                });
            }
        });
        zhouqi=(ChooseTextview) itemView.findViewById(R.id.te2);
        zhouqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("提醒周期", zhouqiList, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        zhouqi.setText(time);
                    }
                });
            }
        });
        tiqian=(ChooseTextview) itemView.findViewById(R.id.te3);
        tiqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("提前天数", tiqianList, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        tiqian.setText(time);
                    }
                });
            }
        });
        tixing=(ChooseTextview) itemView.findViewById(R.id.te4);
        tixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showPicker(BaseActivity.MINU_TIME, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        tixing.setText(time);
                    }
                });
            }
        });
    }

    public NoticeBean getBean(){
        NoticeBean noticeBean=new NoticeBean();
        noticeBean.setTitle(title.getText().toString());
        noticeBean.setMoney(jiedaijine.getText().toString());
        noticeBean.setStage(deadline.getText().toString());
        noticeBean.setRe_money(meiqihuankuan.getText().toString());
        noticeBean.setFirst(shouci.getText().toString());
        String temp1=zhouqi.getText().toString();
        if (temp1!=null){
            noticeBean.setCycle(getposition(zhouqiList,temp1)+"");
        }
        String temp2=tiqian.getText().toString();
        if (temp2!=null){
            noticeBean.setAdvance(getposition(tiqianList,temp2)+"");
        }
        noticeBean.setTime(tixing.getText().toString());
        return noticeBean;
    }



    public static FixNoticeBean TransferBean(NoticeBean noticeBean){
        if (noticeBean==null){
            return null;
        }
        FixNoticeBean fixNoticeBean=new FixNoticeBean();
        fixNoticeBean.setId(noticeBean.getId());
        fixNoticeBean.setInfor1(noticeBean.getTitle());
        fixNoticeBean.setInfor2(noticeBean.getMoney());
        fixNoticeBean.setInfor3(noticeBean.getStage());
        fixNoticeBean.setInfor4(noticeBean.getRe_money());
        fixNoticeBean.setInfor5(noticeBean.getFirst());
        fixNoticeBean.setInfor6(noticeBean.getCycle());
        fixNoticeBean.setInfor7(noticeBean.getAdvance());
        fixNoticeBean.setInfor8(noticeBean.getTime());
        return fixNoticeBean;
    }

    public void initView(NoticeBean noticeBean){
        title.setText(noticeBean.getTitle());
        jiedaijine.setText(noticeBean.getMoney());
        deadline.setText(noticeBean.getStage());
        meiqihuankuan.setText(noticeBean.getRe_money());
        shouci.setText(noticeBean.getFirst());
        zhouqi.setText(noticeBean.getCycle());
        tiqian.setText(noticeBean.getAdvance());
        tixing.setText(noticeBean.getTime());
    }


    public int getposition(String[] strings,String key){
        if (strings!=null && strings.length>0){
            for (int i=0;i<strings.length;i++) {
                if (strings[i].equals(key)){
                    return i+1;
                }
            }
        }
        return 0;
    }

    public boolean checkValidate(){
        if (StringUtils.isNullOrEmpty(title.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(jiedaijine.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(deadline.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(meiqihuankuan.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(shouci.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(zhouqi.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(tiqian.getText().toString())){
            return false;
        }
        if (StringUtils.isNullOrEmpty(tixing.getText().toString())){
            return false;
        }
        return true;
    }
}
