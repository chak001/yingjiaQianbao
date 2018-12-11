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

public class PersonViewHolder3  implements UpdateView {
    public ChooseTextview fangchan,chechan,xinyong;
    public RadioGroup xinyongka,taobao,daikuan;
    BaseActivity baseActivity;
    RadioButton g3_1,g3_2,g4_1,g4_2,g5_1,g5_2;
    public PersonViewHolder3(View itemView) {
        baseActivity= (BaseActivity) itemView.getContext();
        fangchan=(ChooseTextview) itemView.findViewById(R.id.fangchan);
        chechan=(ChooseTextview) itemView.findViewById(R.id.chechan);
        xinyong=(ChooseTextview) itemView.findViewById(R.id.xinyong);
        fangchan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("名下房产", DC.houpro, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        fangchan.setText(time);
                    }
                });
            }
        });
        chechan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("名下车产", DC.carpro, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        chechan.setText(time);
                    }
                });
            }
        });
        xinyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("信用情况", DC.credit, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        xinyong.setText(time);
                    }
                });
            }
        });

        xinyongka=(RadioGroup) itemView.findViewById(R.id.xinyongka);
        taobao=(RadioGroup) itemView.findViewById(R.id.taobao);
        daikuan=(RadioGroup) itemView.findViewById(R.id.daikuan);

        g3_1=(RadioButton) itemView.findViewById(R.id.g3_1);
        g3_2=(RadioButton) itemView.findViewById(R.id.g3_2);
        g4_1=(RadioButton) itemView.findViewById(R.id.g4_1);
        g4_2=(RadioButton) itemView.findViewById(R.id.g4_2);
        g5_1=(RadioButton) itemView.findViewById(R.id.g5_1);
        g5_2=(RadioButton) itemView.findViewById(R.id.g5_2);
    }



    @Override
    public void updateData(Object bean1) {
        count=0;

        PersonBean bean= (PersonBean) bean1;

        if (!StringUtils.getNullString(bean.getHoupro()).equals("")){
            fangchan.setText(""+PersonBean.getName(Integer.valueOf(bean.getHoupro()),DC.houpro));
        }else {
            count++;
        }
        if (!StringUtils.getNullString(bean.getCarpro()).equals("")){
            chechan.setText(""+PersonBean.getName(Integer.valueOf(bean.getCarpro()),DC.carpro));
        }else {
            count++;
        }
        if (!StringUtils.getNullString(bean.getCredit()).equals("")){
            xinyong.setText(""+PersonBean.getName(Integer.valueOf(bean.getCredit()),DC.credit));
        }else {
            count++;
        }

        //信用卡
        int credit= StringUtils.NInt(bean.getCrecard());
        if (credit!=0){
            int flag=credit;
            if (flag==1){
                g3_1.setChecked(true);
            }else {
                g3_2.setChecked(true);
            }
        }else {
            count++;
        }
        //淘宝
        int taobao=StringUtils.NInt(bean.getTb());
        if (taobao!=0){
            int flag=taobao;
            if (flag==1){
                g4_1.setChecked(true);
            }else {
                g4_2.setChecked(true);
            }
        }else {
            count++;
        }
        //贷款记录
        int daikuan=StringUtils.NInt(bean.getLoan());
        if (daikuan!=0){
            int flag=daikuan;
            if (flag==1){
                g5_1.setChecked(true);
            }else {
                g5_2.setChecked(true);
            }
        }else {
            count++;
        }
    }

    @Override
    public void getData(Object bean1) {

        UploadPersonBean bean= (UploadPersonBean) bean1;

        bean.setInfor15(PersonBean.getID(fangchan.getText().toString(),DC.houpro)+"");
        bean.setInfor16(PersonBean.getID(chechan.getText().toString(),DC.carpro)+"");
        bean.setInfor18(PersonBean.getID(xinyong.getText().toString(),DC.credit)+"");

        //信用卡
        int xyid= xinyongka.getCheckedRadioButtonId();
        if (xyid==R.id.g3_1){
            bean.setInfor13("1");
        }else {
            bean.setInfor13("2");
        }
        //淘宝
        int tb= taobao.getCheckedRadioButtonId();
        if (tb==R.id.g4_1){
            bean.setInfor14("1");
        }else {
            bean.setInfor14("2");
        }
        //贷款
        int dk= daikuan.getCheckedRadioButtonId();
        if (dk==R.id.g5_1){
            bean.setInfor17("1");
        }else {
            bean.setInfor17("2");
        }
    }

    private int count=0;
    @Override
    public int getNullCount() {
        return count;
    }
}
