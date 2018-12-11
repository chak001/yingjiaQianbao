package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.fourth.bean.PersonBean;
import nlc.zcqb.app.daichaoview.fourth.bean.UploadPersonBean;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.baseview.view.ChooseTextview;
import nlc.zcqb.baselibrary.callback.TimeClickCallback;
import nlc.zcqb.baselibrary.callback.UpdateView;
import nlc.zcqb.baselibrary.util.StringUtils;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class PersonViewHolder2   implements UpdateView {
    public ChooseTextview shenfen,shouruxingshi,yueshouru,gongsi;
    public RadioGroup gongjijin,shebao;
    BaseActivity baseActivity;
    private RadioButton g1_1,g1_2,g2_1,g2_2;
    private int count=0;
    public PersonViewHolder2(View itemView) {
        baseActivity= (BaseActivity) itemView.getContext();
        shenfen=(ChooseTextview) itemView.findViewById(R.id.shenfen);
        shouruxingshi=(ChooseTextview) itemView.findViewById(R.id.shouruxingshi);
        yueshouru=(ChooseTextview) itemView.findViewById(R.id.yueshouru);
        gongsi=(ChooseTextview) itemView.findViewById(R.id.gongsi);

        shenfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("身份", DC.occu, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        shenfen.setText(time);
                    }
                });
            }
        });
        shouruxingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("收入形式", DC.shouruxingshi, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        shouruxingshi.setText(time);
                    }
                });
            }
        });
        yueshouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("月收入", DC.monin, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        yueshouru.setText(time);
                    }
                });
            }
        });
        gongsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("所在公司", DC.com_type, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        gongsi.setText(time);
                    }
                });
            }
        });


        gongjijin=(RadioGroup) itemView.findViewById(R.id.gongjijin);
        shebao=(RadioGroup) itemView.findViewById(R.id.shebao);
        g1_1=(RadioButton) itemView.findViewById(R.id.g1_1);
        g1_2=(RadioButton) itemView.findViewById(R.id.g1_2);
        g2_1=(RadioButton) itemView.findViewById(R.id.g2_1);
        g2_2=(RadioButton) itemView.findViewById(R.id.g2_2);

    }

    @Override
    public void updateData(Object bean1){
        count=0;

        PersonBean bean= (PersonBean) bean1;
        if (!StringUtils.getNullString(bean.getOccu()).equals("")){
            shenfen.setText(""+PersonBean.getName(Integer.valueOf(bean.getOccu()),DC.occu));
        }else {
            count++;
        }
        if (!StringUtils.getNullString(bean.getInform()).equals("")){
            shouruxingshi.setText(""+PersonBean.getName(Integer.valueOf(bean.getInform()),DC.shouruxingshi));
        }else {
            count++;
        }
        if (!StringUtils.getNullString(bean.getMonin()).equals("")){
            yueshouru.setText(""+PersonBean.getName(Integer.valueOf(bean.getMonin()),DC.monin));
        }else {
            count++;
        }
        if (!StringUtils.getNullString(bean.getCom_type()).equals("")){
            gongsi.setText(""+PersonBean.getName(Integer.valueOf(bean.getCom_type()),DC.com_type));
        }else {
            count++;
        }

        //公积金
        int fund=StringUtils.NInt(bean.getFund());
        if (fund!=0){
            int flag=fund;
            if (flag==1){
                g1_1.setChecked(true);
            }else {
                g2_1.setChecked(true);
            }
        }else {
            count++;
        }
        //社保
        int socse=StringUtils.NInt(bean.getSocse());
        if (socse!=0){
            int flag=socse;
            if (flag==1){
                g2_1.setChecked(true);
            }else {
                g2_2.setChecked(true);
            }
        }else {
            count++;
        }
    }

    @Override
    public void getData(Object bean1) {
        UploadPersonBean bean= (UploadPersonBean) bean1;
        bean.setInfor7(PersonBean.getID(shenfen.getText().toString(),DC.occu)+"");
        bean.setInfor8(PersonBean.getID(shouruxingshi.getText().toString(),DC.shouruxingshi)+"");
        bean.setInfor9(PersonBean.getID(yueshouru.getText().toString(),DC.monin)+"");
        bean.setInfor10(PersonBean.getID(gongsi.getText().toString(),DC.com_type)+"");

        //社保
        int fundid= gongjijin.getCheckedRadioButtonId();
        if (fundid==R.id.g1_1){
            bean.setInfor11("1");
        }else {
            bean.setInfor11("2");
        }
        //公积金
        int socseid= shebao.getCheckedRadioButtonId();
        if (socseid==R.id.g2_1){
            bean.setInfor12("1");
        }else {
            bean.setInfor12("2");
        }

    }

    @Override
    public int getNullCount() {
        return count;
    }


}
