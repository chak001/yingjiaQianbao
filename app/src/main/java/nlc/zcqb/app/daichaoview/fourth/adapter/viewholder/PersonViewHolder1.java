package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.view.View;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.fourth.bean.PersonBean;
import nlc.zcqb.app.daichaoview.fourth.bean.UploadPersonBean;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.baseview.view.ChooseTextview;
import nlc.zcqb.baselibrary.baseview.view.TitleEditview;
import nlc.zcqb.baselibrary.callback.TimeClickCallback;
import nlc.zcqb.baselibrary.callback.UpdateView;
import nlc.zcqb.baselibrary.util.StringUtils;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class PersonViewHolder1  implements UpdateView {
    public TitleEditview edit_name,edit_nickname,edit_telnum,edit_cardid;
    public ChooseTextview marry,culture;
    private BaseActivity baseActivity;
    private int count=0;

    public PersonViewHolder1(View itemView) {

        baseActivity= (BaseActivity) itemView.getContext();
        edit_name=(TitleEditview) itemView.findViewById(R.id.edit_name);
        edit_nickname=(TitleEditview) itemView.findViewById(R.id.edit_nickname);
        edit_telnum=(TitleEditview) itemView.findViewById(R.id.edit_telnum);
        edit_cardid=(TitleEditview) itemView.findViewById(R.id.edit_cardid);

        marry=(ChooseTextview) itemView.findViewById(R.id.marry);
        marry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("婚姻状况", DC.marry, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        marry.setText(time);
                    }
                });
            }
        });
        culture=(ChooseTextview) itemView.findViewById(R.id.culture);
        culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.showValuePicker("文化程度", DC.culture, new TimeClickCallback() {
                    @Override
                    public void click(String time) {
                        culture.setText(time);
                    }
                });
            }
        });
    }

    @Override
    public void updateData(Object bean1){
        count=0;
        PersonBean bean= (PersonBean) bean1;
        edit_name.setText(""+ StringUtils.getNullString(bean.getName()));
        edit_nickname.setText(""+StringUtils.getNullString(bean.getNickname()));
        edit_telnum.setText(""+StringUtils.getNullString(bean.getTel()));
        edit_cardid.setText(""+StringUtils.getNullString(bean.getIden()));
        if (!StringUtils.getNullString(bean.getMarr()).equals("")){
            marry.setText(""+PersonBean.getName(Integer.valueOf(bean.getMarr()),DC.marry));
        }else {
            count++;
        }
        if (!StringUtils.getNullString(bean.getMarr()).equals("")){
            culture.setText(""+PersonBean.getName(Integer.valueOf(bean.getCul()),DC.culture));
        }else {
            count++;
        }

    }

    @Override
    public void getData(Object bean1) {
        UploadPersonBean bean= (UploadPersonBean) bean1;
        bean.setInfor1(edit_name.getText().toString()+"");
        bean.setInfor2(edit_nickname.getText().toString()+"");
        bean.setInfor3(edit_telnum.getText().toString()+"");
        bean.setInfor4(edit_cardid.getText().toString()+"");
        bean.setInfor5(PersonBean.getID(marry.getText().toString(),DC.marry)+"");
        bean.setInfor6(PersonBean.getID(culture.getText().toString(),DC.culture)+"");
    }

    @Override
    public int getNullCount() {
        return count;
    }
}
