package nlc.zcqb.app.daichaoview.fourth.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.customview.validateview.Codecallback;
import com.example.customview.validateview.GetCodeFactory;
import com.example.customview.validateview.GetTel;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CodeBean;
import nlc.zcqb.app.daichaoview.common.CodeCheckBean;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.GetCodePresenter;
import nlc.zcqb.app.daichaoview.common.IdBean;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.XinYongViewHolder1;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.XinYongViewHolder2;
import nlc.zcqb.app.daichaoview.fourth.bean.XInYongNumBean;
import nlc.zcqb.app.daichaoview.fourth.bean.ZhenduanQueryBean;
import nlc.zcqb.app.daichaoview.fourth.view.XInYongResultActivity;
import nlc.zcqb.app.daichaoview.login.LoginActivity;
import nlc.zcqb.app.daichaoview.second.adapter.viewholder.DKDViewHolder1;
import nlc.zcqb.app.daichaoview.second.adapter.viewholder.DKDViewHolder2;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.CheckBoxUtil;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.util.Utils;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class XinYongAdapter extends BaseTypeAdapter implements Codecallback,CommonView{
    private XInYongNumBean numBean=new XInYongNumBean();
    private GetCodePresenter codePresenter;
    private boolean codeCheck=false;
    private CommonSimplePresenter  codeVeriyPresenter,zhenDuanPresenter;
    private String code="";
    private final static int CODECHECK=17;
    private final static int ZHENDUAN=18;
    private XinYongViewHolder2 viewHolder;

    public XinYongAdapter(Context mContext) {
        super(mContext);
        codeVeriyPresenter=new CommonSimplePresenter(this);
        zhenDuanPresenter= new CommonSimplePresenter(this);
        codePresenter=new GetCodePresenter( this);
        ((BaseActivity)mContext).addPresenter(codeVeriyPresenter);
        ((BaseActivity)mContext).addPresenter(zhenDuanPresenter);
        ((BaseActivity)mContext).addPresenter(codePresenter);
    }

    @Override
    public int getTypeByPosition(int position) {
        return position;
    }

    @Override
    public void updateData(Object object) {
        if (object!=null){
            numBean= (XInYongNumBean) object;
            notifyDataSetChanged();
        }
    }

    @Override
    public void loadMoreData(Object object) {

    }

    @Override
    public void TypeFactory(Object object, int type,int UpdateType) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.xinyong_item1,parent,false);
                viewHolder=new XinYongViewHolder1(view);
                break;
            case 1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.xinyong_item2,parent,false);
                viewHolder=new XinYongViewHolder2(view);
                break;
            default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position==0){
            XinYongViewHolder1 viewHolder1=(XinYongViewHolder1) holder;
            viewHolder1.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)mContext).finish();
                }
            });
            viewHolder1.back.setVisibility(View.VISIBLE);
            viewHolder1.title.setText(mContext.getString(R.string.xinyongzhenduan));

            viewHolder1.jigouduijie.setText(numBean.getNum1()+"家");
            viewHolder1.shujuduibi.setText(numBean.getNum2()+"条");
            viewHolder1.blacklist.setText(numBean.getNum3()+"人");
        }
        if (position==1){
            viewHolder=(XinYongViewHolder2) holder;
            CheckBoxUtil.initView(viewHolder.checkBox);
            viewHolder.checkcode.setGetTel(new GetTel() {
                @Override
                public String getTel() {
                    return viewHolder.telnum.getText().toString();
                }
            });
            viewHolder.checkcode.setCode(code);
            viewHolder.checkcode.setFactory(new GetCodeFactory() {
                @Override
                public void getCode(String tel, String imei, String sim) {
                    codePresenter.getCode(tel);
                }
            });
            viewHolder.checkbotton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.checkValidate()){
                        codeVeriyPresenter.getCommonData(CODECHECK,new CodeCheckBean(code,viewHolder.telnum.getText().toString())
                                , DC.xinyongcode,"code");
                    }else {
                        Toast.makeText(mContext,"信息填写不正确或未勾选协议，请重新填写！",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    @Override
    public void success(String key) {
        code=key;
        notifyDataSetChanged();
    }

    @Override
    public void failure(String key) {
        Toast.makeText(mContext,""+key,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success(Object o, int type) {
        if ( ((String)o).equals("100")) {
            if (type == CODECHECK) {
                zhenDuanPresenter.getCommonData(ZHENDUAN,viewHolder.getBean(),DC.xinyongzhenduan,"code");
            } else if (type == ZHENDUAN){
                ARouter.jumpIn(mContext,new Bundle(),XInYongResultActivity.class);
            }
        }else {
            Toast.makeText(mContext,"验证码错误或诊断失败，请重试！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(mContext,(String)o,Toast.LENGTH_SHORT).show();
    }
}
